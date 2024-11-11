package fr.Indyuce.throwables.version;

import org.bukkit.Bukkit;

/**
 * Used to find the version of the server running the plugin
 */
public class ServerVersion {
    private final String version;
    private final int[] integers;

    /*/ used before 1.20 release
    public ServerVersion(Class<?> clazz) {
        version = clazz.getPackage().getName().replace(".", ",").split(",")[3];
        String[] split = version.substring(1).split("\\_");
        integers = new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }*/

    // used before 1.20 release
    public ServerVersion(String bukkitVersion) {/*
        version = bukkitVersion.split("-")[0];
        String[] split = version.substring(1).split(".");
        integers = new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};*/

        // Split the version at the first hyphen ("-") to separate the version part from any suffix
        Bukkit.getServer().getConsoleSender().sendMessage("ServerVersion class bukkitVersion output: " + bukkitVersion);
        String[] parts = bukkitVersion.split("-");
        if (parts.length > 0) {
            version = parts[0];  // Get the version part before the first "-"
        } else {
            // Handle the case where no "-" exists, meaning the version string is just the version number
            version = bukkitVersion;
        }

        // Split the version string at the period (".") to get individual parts
        String[] split = version.split("\\.");
        if (split.length >= 2) {
            integers = new int[]{
                Integer.parseInt(split[0]),  // Major version
                Integer.parseInt(split[1])   // Minor version
            };
        } else {
            // Handle case where the version format is unexpected or incomplete
            integers = new int[]{0, 0};  // Default to zero if format is incorrect
        }
    }

    /**
     * @param version Only two integers, like { 1, 17 } for 1.17
     * @return If the current version is below or equal the given version
     */
    public boolean isBelowOrEqual(int... version) {
        return version[0] > integers[0] || version[1] >= integers[1];
    }

    /**
     * @param version Only two integers, like { 1, 15 } for 1.15.x
     * @return If the current version is higher than given version
     */
    public boolean isStrictlyHigher(int... version) {
        return version[0] < integers[0] || version[1] < integers[1];
        // return !isBelowOrEqual(version);
    }

    public int getRevisionNumber() {
        return Integer.parseInt(version.split("\\_")[2].replaceAll("[^0-9]", ""));
    }

    public int[] toNumbers() {
        return integers;
    }

    @Override
    public String toString() {
        return version;
    }
}
