package core.sistema;

public class Conversor {

    public static double converterParaKB(long bytes) {
        return bytes / 1024.0;
    }

    public static double converterParaMB(long bytes) {
        return bytes / (1024.0 * 1024.0);
    }

    public static double converterParaGB(long bytes) {
        return bytes / (1024.0 * 1024.0 * 1024.0);
    }
}
