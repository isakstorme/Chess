package rules;

public class Move {   // Might be clever to create an interface for move and seperate between normal och special move (castling, en passant...). Might do later if seems like a good idea.
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
