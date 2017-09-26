import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by JaeeonJin on 2017-09-26.
 */
public class Game {

    /**
     * 상수를 사용하는 이유 (매직넘버를 피해야 하는 이유)
     * - 일단 아래 변수는 처음 몇 장의 카드를 받을 때 사용하는 변수다.
     * - 만약 상수를 사용하지 않고, 직접 2라고 표현했을 때 솔직히 다를 건 없다.
     * - 하지만, 어떤 의미인지 알 수 없다는 모호함이 분명히 존재한다.(이로 인해 다른 개발자는 전체 코드를 파악해야하는 상황이 발생한다)
     * - 또한, 여러 메소드에서 2를 사용할 경우 수정이 어렵다. 히스토리를 파악하지 못하면 치명적인 오류가 생길 것이다.
     */
    private static final int INIT_RECEIVED_CARD_COOUNT = 2;
    private static final String STOP_RECEIVE_CARD = "0";
    // 게임의 시작
    public void play(){
        System.out.println("========= Blackjack =========");

        // init
        Scanner sc = new Scanner(System.in);
        Dealer dealer = new Dealer();
        Rule rule = new Rule();
        CardDeck cardDeck = new CardDeck();
        List<Player> players = Arrays.asList(new Gamer("사용자1"), new Dealer()); // 처음 보는 API라 적는다. 인자로 오는 객체들을 리스트로 만드는 것이다.

        // play
        List<Player> initAfterPlayers = initPhase(cardDeck, players);
        List<Player> playingAfterPlayers = playingPhase(sc, cardDeck, initAfterPlayers);

        Player winner = rule.getWinner(playingAfterPlayers);
        System.out.println("승자는 " + winner.getName()+"입니다!");
        // close
        sc.close();
    }

    /**
     * 처음 이 메소드의 두 번째 인자는 Gamer였다. 딜러를 추가하려면 중복된 메소드를 또 다시 생성해야 한다.
     * 하지만 인터페이스를 생성함으로써 함께 처리할 수 있게 되었다.
     */
    private List<Player> initPhase(CardDeck cardDeck, List<Player> players) {
        System.out.println("처음 2장의 카드를 각자 뽑겠습니다.");


        for(int i=0; i<INIT_RECEIVED_CARD_COOUNT; i++) {
            for(Player player : players) {
                showPlayerName(player);
                Card card = cardDeck.draw();
                player.receiveCard(card);
            }
        }

        return players;
    }

    /**
     * 게임 중 카드를 뽑는 단계
     * - 객체는 다른 객체제에 요청을 할 때, 이케/저케/그케 세세하게 요청해서는 안된다.
     * - 객체는 본인의 역할에 충실해야한다.
     * - 이것의 경우, Gamer는 CardDeck에게 카드를 한 장 달라고 요청하기만 하면된다.
     * - CardDeck이 어떤 방식으로 카드를 넘겨주는지 Gamer는 알 필요가 없다.
     * - 단일 책임의 원칙. 각 객체는 각각의 책임이 분명해야한다.
     */
    private List<Player> playingPhase(Scanner sc, CardDeck cardDeck, List<Player> players) {
        List<Player> cardReceivedPlayers;

        while(true) {
            cardReceivedPlayers = receiveCardAllPlayers(sc, cardDeck, players);
            if( isAllPlayerTurnOff(cardReceivedPlayers) ) break;
        }

        return players;
    }

    private boolean isAllPlayerTurnOff(List<Player> players) {
        for(Player player : players) {
            if( player.isTurn())
                return false;
        }

        return true;
    }

    // 모든 플레이어에게 카드 주기

    /**
     * 본래 이 메소드의 목적은 플레이어들이 카드를 받도록 하는 것이다.
     * 그런데 현재 이 메소드는 두 가지 일을 하고 있다.
     * 1. 카드 받기
     * 2. 카드를 받았다는 신호 보내기
     * 단일 책임의 원칙에 따라 분리하려면 2번째 행위는 플레이어가 직접 해야한다. 그 결과 turnOn/Off isTurn 메소드가 인터페이스에 추가되었다.
     *
     * 또한 List를 반환한다는 점에 대해서 생각해보자
     * 자바의 컬렉션이나 인스턴스는 Java의 특성으로 인해 Call By Reference 이다.
     * 그럼에도 불구하고 List를 반환하는 이유는 메소드의 목적을 명확히 하기 위해서이다.
     * "CardDeck과 List를 인자로 받아 특정 과정을 통해 변경된 List를 준다" 이것이 바로 목적이다.
     * 만약 void로 할 경우 list가 번경은 될지언정, 최종적으로 무얼 위함인지 코등사에서 확인하기 어렵다.
     */
    private List<Player> receiveCardAllPlayers(Scanner sc, CardDeck cardDeck, List<Player> players) {
        for(Player player : players) {
            showPlayerName(player);

            if( isReceiveCard(sc) ) {
                Card card = cardDeck.draw();
                player.receiveCard(card);
                player.turnOn();
            } else {
                player.turnOff();
            }
        }

        return players;
    }

    private boolean isReceiveCard(Scanner sc) {
        System.out.println("카드를 뽑겠습니까? 종료를 원하시면 0을 입력하세요.");
        return !STOP_RECEIVE_CARD.equals(sc.nextLine());
    }

    private void showPlayerName(Player player) {
        System.out.println(player.getName()+"님 차례입니다.");
    }
}