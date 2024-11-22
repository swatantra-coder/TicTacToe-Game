import java.util.*;

public class TicTacToeGame {

    Deque<Player> players;
    Board gameBoard;

    TicTacToeGame (){
        initializeGame();
    }

    public void initializeGame(){
        players =new LinkedList<>();

        // Create Players
        PlayingPieceO o = new PlayingPieceO();  // O goes first
        players.add(new Player("Player 1", o));

        PlayingPieceX x = new PlayingPieceX();
        players.add(new Player("Player 2", x));

        gameBoard = new Board(3);
    }

    public String startGame(){
        Boolean noWinner = true;

        while(noWinner){
            // Take out the player whose turn it is and put it back at the end of the queue
            Player playerTurn=players.removeFirst();

            //get free spaces
            gameBoard.printBoard();
            List<Map.Entry<Integer,Integer>> freeSpaces = gameBoard.getFreeCells();
            if(freeSpaces.size()==0){
                noWinner=false;
                continue;
            }

            //read the user input
            System.out.println("Player:"+playerTurn.name+"Enter row and column:");
            Scanner scanner = new Scanner(System.in); // Create a Scanner object
            String s = scanner.nextLine();  // Read user input
            String[] values = s.split(",");

            int inputRow = Integer.valueOf(values[0]);
            int inputCol = Integer.valueOf(values[1]);

            //place the piece
            boolean piecePlaced = gameBoard.addPiece(playerTurn.playingPiece, inputRow, inputCol);
            if(!piecePlaced){
                //invalid move
                System.out.println("Invalid move, try again");
                //put the player back at the end of the queue
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isThereWinner(inputRow,inputCol,playerTurn.playingPiece.pieceType);
            if(winner){
                return playerTurn.name;
            }
        }
        return "Tie";
    }

    public boolean isThereWinner(int inputRow,int inputCol,PieceType pieceType){
        boolean rowMatch= true;
        boolean colMatch= true;
        boolean diagonalMatch= true;
        boolean revDiagonalMatch= true;

        //check row
        for(int i=0;i<gameBoard.size;i++){
            if(gameBoard.board[inputRow][i]==null || gameBoard.board[inputRow][i].pieceType!=pieceType){
                rowMatch=false;
                break;
            }
        }

        //check column
        for(int i=0;i<gameBoard.size;i++){
            if(gameBoard.board[i][inputCol]==null || gameBoard.board[i][inputCol].pieceType!=pieceType){
                colMatch=false;
                break;
            }
        }

        //check diagonal
        for(int i=0;i<gameBoard.size;i++){
            if(gameBoard.board[i][i]==null || gameBoard.board[i][i].pieceType!=pieceType){
                diagonalMatch=false;
                break;
            }
        }

        //check reverse diagonal
        for(int i=0;i<gameBoard.size;i++){
            if(gameBoard.board[i][gameBoard.size-i-1]==null || gameBoard.board[i][gameBoard.size-i-1].pieceType!=pieceType){
                revDiagonalMatch=false;
                break;
            }
        }

        return rowMatch || colMatch || diagonalMatch || revDiagonalMatch;
    }
}
