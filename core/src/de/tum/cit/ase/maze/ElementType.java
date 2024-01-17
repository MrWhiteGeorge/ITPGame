package de.tum.cit.ase.maze;

public enum ElementType {
    Wall, Entry, Exit, Trap, Enemy, Key, Floor;

    public static ElementType findByOrdinal(int ordinal) {
        for (var type : values()) {
            if (ordinal == type.ordinal()) {
                return type;
            }
        }
        return null;
    }
}
