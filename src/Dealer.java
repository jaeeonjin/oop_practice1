import java.util.ArrayList;
import java.util.List;

/**
 * Created by JaeeonJin on 2017-09-26.
 * - 카드를 받는다.
 * - 단, 2카드의 합계 점수가 16점 이하이면 반드시 1장을 추가로 뽑고, 17점 이상이면 받을 수 없다.
 * - 뽑은 카드를 소유한다.
 * - 카드를 오픈한다.
 */
public class Dealer implements Player {

    private List<Card> cards;
    private boolean turn;
    private static final int CAN_RECEIVE_POINT = 16;
    private static final String name = "딜러";

    public Dealer() {
        cards = new ArrayList<>();
    }

    @Override
    public void receiveCard(Card card) {
        if( this.isReceiveCard() ) {
            this.cards.add(card);
            this.showCards();
        }
    }

    @Override
    public List<Card> openCards(){
        return this.cards;
    }

    @Override
    public void turnOn() {
        this.turn = true;
    }

    @Override
    public void turnOff() {
        this.turn = false;
    }

    @Override
    public boolean isTurn() {
        return this.turn;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void showCards(){
        StringBuilder sb = new StringBuilder();
        sb.append("현재 보유 카드 목록 \n");

        for(Card card : cards){
            sb.append(card.toString());
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    /**
     * 이 메소드를 만들어야 하는 이유.
     * - 사실 직접적으로 명시해도 된다.
     * - 하지만, 만약 직접적으로 명시했을 때 이 조건문이 정확이 어떤 일을 하는지 추측, 파악할 수 밖에 없다.
     * - 이렇게 하면 타인이 코드를 볼 때 명확히 알 수 있다.
     * - 주석이 설명하는 것이 아니다. 명확한 코드와 변수명, 메소드명이 해야하는 것이다.
     * @return
     */
    private boolean isReceiveCard() {
        return getPointSum() <= CAN_RECEIVE_POINT;
    }

    private int getPointSum() {
        int sum = 0;
        for(Card card : cards) {
            sum += card.getPoint();
        }
        return sum;
    }
}
