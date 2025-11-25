package nfm.lit;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.datatransfer.DataFlavor;

/**
 * Source Engine style Developer console for Need For Madness
 *
 * @author oteek
 */
public class DevTool {
    public boolean active = false;
    private ArrayList<String> consoleLog = new ArrayList<>();
    private StringBuilder currentInput = new StringBuilder();
    
    private List<String> commandHistory;
    private int historyIndex;

    public boolean godmode = false;
    public boolean debugstat = false;
    public int oldclrad;

    private Map<String, String> commandDescriptions; // for help command

    // Game references
    private GameSparker gamesparker;
    private CheckPoints checkpoints;
    private Madness[] madness;
    private ContO[] conto;
    private ContO[] conto1;
    private xtGraphics xt;

    public DevTool(GameSparker gamesparker, CheckPoints checkpoints, Madness madness[], ContO conto[], ContO conto1[], xtGraphics xt) {
        this.gamesparker = gamesparker;
        this.checkpoints = checkpoints;
        this.madness = madness;
        this.conto = conto;
        this.conto1 = conto1;
        this.xt = xt;

        commandHistory = new ArrayList<>();
        historyIndex = -1;

        commandDescriptions = new HashMap<>();
        populateCommandDescriptions();
    }

    public void draw(Graphics2D g, int width, int height) {
        if (!active) return;

        int consoleHeight = height / 2;
        
        // Background
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, width, consoleHeight);
        
        // Border
        g.setColor(new Color(255, 128, 0));
        g.drawLine(0, consoleHeight, width, consoleHeight);

        // Text setup
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontMetrics fm = g.getFontMetrics();
        int lineHeight = fm.getHeight();
        int y = consoleHeight - 10;

        // Draw Input Line
        g.setColor(Color.WHITE); // Red for errors
        String inputStr = "> " + currentInput.toString() + "_";
        g.drawString(inputStr, 10, y);
        y -= lineHeight;

        // Draw Log History
        for (int i = consoleLog.size() - 1; i >= 0; i--) {
            String line = consoleLog.get(i);

            // Set color based on message type
            if (line.contains("[ERROR]")) {
                g.setColor(Color.RED); // Red for errors
            } else if (line.contains("[WARN]")) {
                g.setColor(Color.YELLOW); // Yellow for warnings
            } else if (line.contains("[INFO]")) {
                g.setColor(Color.ORANGE); // Orange for info
            } else if (line.contains("[DEBUG]")) {
                g.setColor(Color.BLUE); // Blue for debug
            } else {
                g.setColor(Color.WHITE); // Default color for other messages
            }

            g.drawString(line, 10, y);

            y -= lineHeight;
            if (y < 0) break;
        }
    }

    public void input(int key) {
        if (!active) return;

        if (key == 10) { // Enter
            String command = currentInput.toString();
            if (!command.trim().isEmpty()) {
                executeCommand(command);
                commandHistory.add(command);
                historyIndex = commandHistory.size();
            }
            currentInput.setLength(0);
        } else if (key == 8) { // Backspace
            if (currentInput.length() > 0) {
                currentInput.setLength(currentInput.length() - 1);
            }
        } else if (key == 1004) { // Up Arrow (AWT Event)
            if (historyIndex > 0) {
                historyIndex--;
                currentInput.setLength(0);
                currentInput.append(commandHistory.get(historyIndex));
            } else if (historyIndex == 0 && !commandHistory.isEmpty()) {
                 currentInput.setLength(0);
                 currentInput.append(commandHistory.get(0));
            }
        } else if (key == 1005) { // Down Arrow (AWT Event)
            if (historyIndex < commandHistory.size() - 1) {
                historyIndex++;
                currentInput.setLength(0);
                currentInput.append(commandHistory.get(historyIndex));
            } else {
                historyIndex = commandHistory.size();
                currentInput.setLength(0);
            }
        } else if (key >= 32 && key <= 126) { // Printable characters
            currentInput.append((char) key);
        } else if (key == 22) { // ctrl + v
            try {
                String clipboardText = Toolkit.getDefaultToolkit()
                                            .getSystemClipboard()
                                            .getData(DataFlavor.stringFlavor)
                                            .toString();
                currentInput.append(clipboardText);
            } catch (Exception e) {
                print("Failed to paste from clipboard: " + e.getMessage());
            }
        }
    }

    public void print(String s) {
        String[] lines = s.split("\n");
        for (String line : lines) {
           consoleLog.add(line);
        }
        // Keep log size manageable
        if (consoleLog.size() > 100) {
            consoleLog.subList(0, consoleLog.size() - 100).clear();
        }
    }

    private void executeCommand(String command) {
        print("> " + command);

        String[] parts = command.split(" ");
        String commandName = parts[0];
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        switch (commandName) {
            case "quit":
                RunApp.exitSequence();
                break;
            case "exit":
                RunApp.exitSequence();
                break;
            case "crash":
                throw new RuntimeException("Test crash!");
            case "nplayers":
                if (args.length == 1) {
                    try {
                        int nplayers = Integer.parseInt(args[0]);
                        if (GameSparker.gameStateID > 1) {
                            if ((nplayers >= 1 && nplayers <= 51)) {
                                xt.nplayers_debug = true;
                                xt.nplayers_override = nplayers;
                                print("Numbers of players set to " + nplayers + ", overriden for all stages.");
                            } else if (nplayers == 0) {
                                xt.nplayers_debug = false;
                                print("Number of players are now determined by stage\n(xtGraphics, public void carspergame)");
                            } else {
                                print("Invalid player number.");
                            }
                        } else {
                            print("This command only works in menus and before stage select.");
                        }
                    } catch (NumberFormatException e) {
                        print("Invalid number of players.\n");
                    }
                } else {
                    print("nplayers is " + GameFacts.numberOfPlayers + "\nUsage: nplayers <0-51>");
                }
                break;
            case "iconpos":
                if (args.length == 5) {
                    try {
                        int off1 = Integer.parseInt(args[0]);
                        int off2 = Integer.parseInt(args[1]);
                        int carX = Integer.parseInt(args[2]);
                        int carY = Integer.parseInt(args[3]);
                        int carZ = Integer.parseInt(args[4]);
                        xt.roffsetX = off1;
                        xt.roffsetY = off2;
                        xt.rcarX = carX;
                        xt.rcarY = carY;
                        xt.rcarZ = carZ;
                        print("Set icon preview position: camX=" + off1 + " camY=" + off2 + " carX=" + carX + " carY=" + carY + " carZ=" + carZ);
                    } catch (NumberFormatException e) {
                        print("Invalid argument. All parameters must be integers.");
                    }
                } else {
                    print("Usage: iconpos <camX> <camY> <carX> <carY> <carZ>");
                }
                break;
            case "give":
                if (args.length == 1) {
                    try {
                        int n = Integer.parseInt(args[0]);
                        GameSparker.ownedCarIds.add(n);
                        print("Added car ID " + n + " to garage.");
                    } catch (NumberFormatException e) {
                        print("Invalid argument. All parameters must be integers.");
                    }
                } else {
                    print("Usage: give <id>");
                }
                break;
            case "fix":
                if (args.length == 1) {
                    try {
                        int n = Integer.parseInt(args[0]);
                        if (GameSparker.gameStateID == 0) {
                            madness[n].devFixCar();
                            print("Car " + n + " fixed");
                        } else {
                            print("This command only works in game.");
                        }
                    } catch (NumberFormatException e) {
                        print("Invalid argument.");
                    }
                } else {
                    print("Usage: fix <n>");
                }
                break;
            case "god":
                if (GameSparker.gameStateID == 0) {
                    if (!godmode) {
                        oldclrad = madness[0].stat.clrad;
                        madness[0].stat.clrad = 0;
                        print("Godmode ON");
                        godmode = true;
                    } else {
                        madness[0].stat.clrad = oldclrad;
                        print("Godmode OFF");
                        godmode = false;
                    }
                } else {
                    print("This command only works in game.");
                }
                break;
            case "spectate":
                if (args.length == 1) {
                    try {
                        int n = Integer.parseInt(args[0]);
                        if (GameSparker.gameStateID == 0) {
                            if (n > 0 && n < GameFacts.numberOfPlayers) {
                                xt.spectate = n;
                                print("Spectating [AI]" + StatList.name[xt.sc[n]]);
                            } else if (n == 0) {
                                xt.spectate = n;
                                print("Spectating [Player]" + StatList.name[xt.sc[n]]);
                            } else {
                                print("Invalid player ID.");
                            }
                        } else {
                            print("This command only works in game.");
                        }
                    } catch (NumberFormatException e) {
                        print("Invalid argument.");
                    }
                } else {
                    print("Usage: spectate <n>");
                }
                break;
            case "debug":
                if (!debugstat) {
                    xt.debugmode = true;
                    print("Debug mode enabled.");
                    debugstat = true;
                } else {
                    xt.debugmode = false;
                    print("Debug mode disabled.");
                    debugstat = false;
                }
                break;
            case "unlocked":
                if (args.length == 1) {
                    try {
                        int n = Integer.parseInt(args[0]);
                        xt.unlocked = n;
                        print("xtGraphics.unlocked set to " + n);
                    } catch (NumberFormatException e) {
                        print("Invalid argument.");
                    }
                } else {
                    print("xtGraphics.unlocked is " + xt.unlocked + "\nUsage: unlocked <n>");
                }
                break;
            case "name":
                if (args.length == 1) {
                    try {
                        String n = args[0];
                        xt.playerId = n;
                        print("Player name set to " + n);
                    } catch (NumberFormatException e) {
                        print("Invalid argument.");
                    }
                }
                break;
            case "fase":
                if (args.length == 1) {
                    try {
                        int n = Integer.parseInt(args[0]);
                        xt.fase = Phase.valueOfValue(n);
                        print("Set xtGraphics.fase to " + n);
                    } catch (NumberFormatException e) {
                        print("Invalid argument.");
                    }
                } else {
                    print("fase: " + xt.fase.toString());
                }
                break;
            case "spawn_ai":
                if (GameSparker.gameStateID == 0) {

                    int n = GameFacts.numberOfPlayers + 1;

                    conto[n] = new ContO(conto1[xt.sc[n]], 0, 250 - conto1[xt.sc[n]].grat, -760 + ((n / 3) * 760), 0);
                    madness[n].reseto(xt.sc[n], conto[n], checkpoints);
                    print("Spawned " + StatList.name[xt.sc[n]]);
                } else {
                    print("This command only works in game.");
                }
                break;
            case "nfm":
                if (args.length == 1) {
                    try {
                        int n = Integer.parseInt(args[0]);
                        if (GameSparker.gameStateID == 10) {
                            if ((n >= 1 && n <= 3)) {
                                xtGraphics.nfmMode = n;
                                if (n < 2) {
                                    print("Need For Madness " + n);
                                } else {
                                    print("Freeplay mode");
                                }
                            } else {
                                print("Prevented from creating a paradox.");
                            }
                        } else {
                            print("This command only works in main menu.");
                        }
                    } catch (NumberFormatException e) {
                        print("Invalid argument.");
                    }
                } else {
                    print("Usage: nfm <n>");
                }
                break;
            case "map":
                if (args.length == 1) {
                    try {
                        String path = args[0];
                        xtGraphics.nfmMode = 3;
                        print("Loading stage from: " + path + ".txt");
                        GameSparker.loadStageCus = path;    //idk
                        checkpoints.stage = -1;
                        if (GameSparker.gameStateID == 0) {
                            xt.fase = Phase.LOADSTAGE2;
                        }
                        if (GameSparker.gameStateID == 1 || GameSparker.gameStateID == 3) {
                            xt.fase = Phase.LOADSTAGE;
                        }
                    } catch (NumberFormatException e) {
                        print("Invalid argument.");
                    }
                } else {
                    print("Usage: map <dir to .txt>");
                }
                break;
            case "stagesubdir":
                if (args.length == 1) {
                    try {
                        String sub = args[0];
                            GameSparker.stageSubDir = sub + "/";    //idk
                            xtGraphics.nfmMode = 3;
                            print("Set stage subdir to " + sub);
                    } catch (NumberFormatException e) {
                        print("Invalid argument.");
                    }
                } else {
                    print("Usage: stagesubdir <subdir>");
                }
                break;
            case "status":
                print("Game State: " + GameSparker.gameState);
                break;
            case "connect":
                if (args.length == 1) {
                    String sub = args[0];
                    try {
                        // Allow localhost, domains, and IPs
                        String regex = "^([a-zA-Z0-9.-]+):(\\d{1,5})$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(sub);
            
                        if (matcher.matches()) {
                            String host = matcher.group(1);
                            int port = Integer.parseInt(matcher.group(2));
            
                            if (port >= 0 && port <= 65535) {
                                print("Connecting to " + host + " on port " + port + "...");
            
                                try {
                                    xt.socket = new Socket(host, port);
                                    print("Connected to the server");
            
                                    xt.serverResponse = new BufferedReader(new InputStreamReader(xt.socket.getInputStream()));
                                    String message = xt.serverResponse.readLine();
                                    print("Server says: " + message);
            
                                } catch (java.net.ConnectException e) {
                                    print("Connection refused: " + e.getMessage());
                                } catch (IOException e) {
                                    print("An error occurred:\n" + e.toString());
                                } finally {
                                    try {
                                        if (xt.serverResponse != null) xt.serverResponse.close();
                                        if (xt.socket != null) xt.socket.close();
                                    } catch (IOException e) {
                                        print("An error occurred while closing connection:\n" + e.toString());
                                    }
                                }
            
                            } else {
                                print("Port must be between 0 and 65535.");
                            }
                        } else {
                            print("Invalid host:port format.");
                        }
                    } catch (NumberFormatException e) {
                        print("Invalid port number.");
                    }
                } else {
                    print("Usage: connect <host:port>");
                }
                break;
            case "clear":
                consoleLog.clear();
                break;
            case "help":
                if (args.length == 0) {
                    print("Available commands:");
                    for (String cmd : commandDescriptions.keySet()) {
                        print(cmd);
                    }
                } else if (args.length == 1) {
                    String helpCommand = args[0];
                    if (commandDescriptions.containsKey(helpCommand)) {
                        print(helpCommand + ": " + commandDescriptions.get(helpCommand));
                    } else {
                        print("No help available for unknown command: " + helpCommand);
                    }
                } else {
                    print("Usage: help <command>");
                }
                break;
            default:
                print("Unknown command: " + commandName);
                break;
        }
    }
    
    private void populateCommandDescriptions() {
        commandDescriptions.put("debug", "Enables/disables debug information.");
        commandDescriptions.put("nplayers", "Sets the number of players. Usage: nplayers <1-51>");
        commandDescriptions.put("fix", "Fixes a specified car. Usage: fix <n>");
        commandDescriptions.put("spectate", "Spectates a specified car. Usage: spectate <n>");
        commandDescriptions.put("god", "Toggles god mode.");
        commandDescriptions.put("unlocked", "Sets the unlocked value in xtGraphics. Usage: unlocked <n>");
        commandDescriptions.put("fase", "Sets the game state (phase). Usage: fase <n>");
        commandDescriptions.put("nfm", "Sets the NFM mode. Usage: nfm <n>");
        commandDescriptions.put("map", "Loads a stage from the specified path. Usage: map <path-to-.txt>");
        commandDescriptions.put("stagesubdir", "Sets the stage subdirectory. Usage: stagesubdir <subdir>");
        commandDescriptions.put("status", "Displays the current game state.");
        commandDescriptions.put("clear", "Clears the console.");
        commandDescriptions.put("connect", "Connects to a test server. Usage: connect <ip:port>");
        commandDescriptions.put("help", "Displays help information. Usage: help <command>");
    }
}