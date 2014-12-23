/**
 * Created by BC on 23/12/14.
 */
public class Multiplayer {
    Player player1;
    Player player2;

    public Multiplayer(){

    }

    public boolean isFirstPlayersTurn(){
        if(!isSecondPlayersTurn()){
            return true;
        }else {
            return false;
        }
    }

    public boolean isSecondPlayersTurn(){
        if(!isFirstPlayersTurn()){
            return true;
        }else {
            return false;
        }
    }



}
