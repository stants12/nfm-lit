package nfm.lit;
import java.io.PrintStream;

public class DevToolPrintStream extends PrintStream {
    private final PrintStream original;
    private final DevTool devTool;

    public DevToolPrintStream(PrintStream original, DevTool devTool) {
        super(original);
        this.original = original;
        this.devTool = devTool;
    }

    @Override
    public void println(String x) {
        // Print to the original stream (console)
        original.println(x);

        // Print to the DevTool console if active
        if (devTool != null) {
            devTool.print(x);
        }
    }

    @Override
    public void print(String x) {
        // Print to the original stream (console)
        original.print(x);

        // Print to the DevTool console if active
        if (devTool != null) {
            devTool.print(x);
        }
    }
}