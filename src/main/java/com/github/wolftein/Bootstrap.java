package com.github.wolftein;

public class Bootstrap {
    private final static String ADDRESS = "0x9197728D61BFd3695071e385C12B1D5E041bC44f";

    private final static String SECRET = "GET_YOUR_OWN_SECRET_KEY";

    public static void main(String[] argv) {
        new Bot(ADDRESS, SECRET, Boolean.parseBoolean(argv[0]), Boolean.parseBoolean(argv[1])).run();
    }
}
