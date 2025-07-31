package rules;

public class PromotionMove implements Move {    //Maybe I should use record instead
    private NumCoordinate from;
    private NumCoordinate to;
    private Character piece;

    @Override
    public NumCoordinate from() {
        return from;
    }

    @Override
    public NumCoordinate to() {
        return to;
    }

    public Character piece(){
        return piece;
    }

    public PromotionMove(NumCoordinate from, NumCoordinate to, Character piece){
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

}
