package rules;

// This move class is defined relative to starting position, so does not give away information about starting position and final position of move.
public class Move {   
    public int deltaFile;
    public int deltaRank;


    public Move(int deltaFile, int deltaRank){
        this.deltaFile = deltaFile;
        this.deltaRank = deltaRank;
    }

    @Override
    public String toString() {
        return Integer.toString(deltaFile) + " " + Integer.toString(deltaRank);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (!(obj instanceof Move)){
            return false;
        }
        Move obj2 = (Move) obj;
        return this.deltaFile == obj2.deltaFile && this.deltaRank == obj2.deltaRank;
    }
}
