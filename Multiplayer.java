/**
 * Created by BC on 23/12/14.
 */
public class Multiplayer {
    public int player1Counter = 0;
    public int player2Counter = 0;

    public Multiplayer(){

    }

    public boolean isFirstPlayersTurn(){  //when their counters are equal it is player1's turn
        if(player1Counter == player2Counter){
            return true;
        }else {
            return false;
        }
    }

    public boolean isSecondPlayersTurn(){ //when their counters are not equal it is player2's turn
        if(player1Counter != player2Counter){
            return true;
        }else {
            return false;
        }
    }



}
