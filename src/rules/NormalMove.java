package rules;

public class NormalMove implements Move{
    private NumCoordinate from;
    private NumCoordinate to;

    @Override
    public NumCoordinate from() {
        return from;
    }

    @Override
    public NumCoordinate to() {
        return to;
    }
    public NormalMove(NumCoordinate from, NumCoordinate to){
        this.from = from;
        this.to = to;
    }
}
