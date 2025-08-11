## Need For Madness: LIT

Need For Madness: LIT (NFML) is a decompilation and subsequent improvement of Need For Madness 2, developed by Radicalplay.com.

It is designed to provide a smooth gameplay experience for the vanilla game (with tweaks - see below) as well as a solid foundation for modifying and building upon for prospective hackers.
In order to achieve this, much of the original source code needs rewriting. While some work has been done, much more needs doing, so please feel free to contribute!

Some of the work already done includes:
 - Providing a complete, playable, version of NFM2.
 - Improving 2D graphics rendering to not rely on a hardcoded screen resolution (however, dynamic scaling is not yet supported).
 - Improving the fluency and look & feel of the overall game.
 - Rendering at 720p by default.
 - Providing a much more reasonable source tree, grouping the source files into subfolders where appropriate.

However, much more work needs doing. There are still hardcoded values all over the code relating to rendering, AI, stages, cars, and more. 
Ideally, all of these subsystems should be decoupled from each other in order to facilitate a much smoother hacking experience.

Special thanks to:
 - cravxx (Kaffeinated) for the initial decompilation and tidy-up of NFM2.
 - G6 for providing some helpful hacks to improve stage select visuals.
 - stants12 (oteek) for further building upon and improving the original decompilation.
 - FlameCharge (Jacherr) for fixing UI to work properly at 720p resolutions.
 - Everyone else who offered improvements, fixes, and anything else!

Need for Madness 2 (c) Radicalplay.com.
All changes and improvements (c) their original authors. See @author documentation fields or other comments where appropriate.