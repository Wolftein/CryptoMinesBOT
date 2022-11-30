package com.github.wolftein;

import java.io.IOException;

public class Bootstrap {
    private final static String ADDRESS = "0x756dB42886D700B4553A5bfffB3FfeaE20159e5E";

    private final static String SECRET = "GET_YOUR_OWN_PRIVATE_KEY";

    public static void main(String[] argv) throws IOException {
        boolean isUnlist = (argv.length >= 3 && Boolean.parseBoolean(argv[2]));

        new Bot(ADDRESS, SECRET, argv[0], Boolean.parseBoolean(argv[1]), isUnlist).run();
    }
}
