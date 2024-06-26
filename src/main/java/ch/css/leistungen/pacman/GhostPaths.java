package ch.css.leistungen.pacman;

import java.util.List;

public class GhostPaths {


    public static final List<int[]> BLUE_GHOST_PATH = List.of(
            new int[]{140, 180},
            new int[]{140, 160},
            new int[]{120, 160},
            new int[]{100, 160},
            new int[]{80, 160},
            new int[]{80, 140},
            new int[]{80, 120},
            new int[]{80, 100},
            new int[]{100, 100},
            new int[]{120, 100},
            new int[]{140, 100},
            new int[]{140, 80},
            new int[]{140, 60},
            new int[]{160, 60},
            new int[]{180, 60},
            new int[]{180, 80},
            new int[]{180, 100},
            new int[]{180, 120},
            new int[]{180, 140},
            new int[]{180, 160},
            new int[]{180, 180},
            new int[]{200, 180},
            new int[]{220, 180},
            new int[]{240, 180},
            new int[]{260, 180},
            new int[]{280, 180},
            new int[]{300, 180},
            new int[]{320, 180},
            new int[]{320, 160},
            new int[]{320, 140},
            new int[]{300, 140},
            new int[]{280, 140},
            new int[]{260, 140},
            new int[]{260, 120},
            new int[]{260, 100},
            new int[]{260, 80},
            new int[]{240, 80},
            new int[]{220, 80},
            new int[]{220, 60},
            new int[]{200, 60},
            new int[]{180, 60},
            new int[]{180, 80},
            new int[]{180, 100},
            new int[]{180, 120},
            new int[]{180, 140},
            new int[]{180, 160},
            new int[]{180, 180},
            new int[]{160, 180},
            new int[]{140, 180},
            new int[]{140, 200},
            new int[]{120, 200},
            new int[]{100, 200},
            new int[]{80, 200},
            new int[]{60, 200},
            new int[]{60, 220},
            new int[]{40, 220},
            new int[]{20, 220},
            new int[]{0, 220},
            new int[]{0, 240},
            new int[]{0, 260},
            new int[]{0, 280},
            new int[]{0, 300},
            new int[]{20, 300},
            new int[]{40, 300},
            new int[]{60, 300},
            new int[]{80, 300},
            new int[]{80, 280},
            new int[]{80, 260},
            new int[]{80, 240},
            new int[]{80, 220},
            new int[]{80, 200},
            new int[]{100, 200},
            new int[]{120, 200},
            new int[]{140, 200}
    );


    public static final List<int[]> RED_GHOST_PATH = List.of(
            new int[]{320, 0},
            new int[]{320, 20},
            new int[]{320, 40},
            new int[]{320, 60},
            new int[]{320, 80},
            new int[]{300, 80},
            new int[]{280, 80},
            new int[]{260, 80},
            new int[]{240, 80},
            new int[]{220, 80},
            new int[]{220, 60},
            new int[]{220, 40},
            new int[]{220, 20},
            new int[]{220, 0},
            new int[]{220, 20},
            new int[]{220, 40},
            new int[]{220, 60},
            new int[]{200, 60},
            new int[]{180, 60},
            new int[]{160, 60},
            new int[]{140, 60},
            new int[]{140, 40},
            new int[]{140, 20},
            new int[]{140, 0},
            new int[]{120, 0},
            new int[]{100, 0},
            new int[]{80, 0},
            new int[]{60, 0},
            new int[]{40, 0},
            new int[]{40, 20},
            new int[]{40, 40},
            new int[]{40, 60},
            new int[]{40, 80},
            new int[]{40, 100},
            new int[]{40, 120},
            new int[]{40, 140},
            new int[]{40, 160},
            new int[]{20, 160},
            new int[]{0, 140},
            new int[]{0, 120},
            new int[]{0, 100},
            new int[]{0, 80},
            new int[]{0, 60},
            new int[]{0, 40},
            new int[]{0, 20},
            new int[]{0, 0},
            new int[]{20, 0},
            new int[]{40, 0},
            new int[]{60, 0},
            new int[]{80, 0},
            new int[]{100, 0},
            new int[]{120, 0},
            new int[]{140, 0},
            new int[]{160, 0},
            new int[]{180, 0},
            new int[]{200, 0},
            new int[]{220, 0},
            new int[]{240, 0},
            new int[]{260, 0},
            new int[]{280, 0},
            new int[]{300, 0},
            new int[]{320, 0}
    );

    public static final List<int[]> GREEN_GHOST_PATH = List.of(
            new int[]{280, 300},
            new int[]{280, 280},
            new int[]{280, 260},
            new int[]{280, 240},
            new int[]{280, 220},
            new int[]{300, 220},
            new int[]{320, 220},
            new int[]{320, 200},
            new int[]{320, 180},
            new int[]{300, 180},
            new int[]{280, 180},
            new int[]{260, 180},
            new int[]{240, 180},
            new int[]{220, 180},
            new int[]{200, 180},
            new int[]{180, 180},
            new int[]{160, 180},
            new int[]{140, 180},
            new int[]{140, 200},
            new int[]{120, 200},
            new int[]{100, 200},
            new int[]{80, 200},
            new int[]{80, 220},
            new int[]{60, 220},
            new int[]{40, 220},
            new int[]{20, 220},
            new int[]{0, 220},
            new int[]{0, 240},
            new int[]{0, 260},
            new int[]{0, 280},
            new int[]{0, 300},
            new int[]{0, 320},
            new int[]{0, 300},
            new int[]{20, 300},
            new int[]{40, 300},
            new int[]{60, 300},
            new int[]{80, 300},
            new int[]{100, 300},
            new int[]{120, 300},
            new int[]{140, 300},
            new int[]{140, 320},
            new int[]{160, 320},
            new int[]{180, 320},
            new int[]{200, 320},
            new int[]{220, 320},
            new int[]{220, 300},
            new int[]{240, 300},
            new int[]{260, 300},
            new int[]{280, 300}
    );
}
