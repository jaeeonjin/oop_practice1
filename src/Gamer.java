import java.util.ArrayList;
import java.util.List;

/**
 * Created by JaeeonJin on 2017-09-26.
 * - 카드를 받는다
 * - 뽑은 카드를 소유한다.
 * - 카드를 오픈한다.
 */
public class Gamer implements Player {
    private List<Card> cards;
    private boolean turn;
    private String name;

    public Gamer(String name) {
        cards = new ArrayList<>();
        this.name =  name;
    }

    @Override
    public void receiveCard(Card card) {
        this.cards.add(card);
        this.showCards();
    }

    @Override
    public List<Card> openCards() {
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

    // Gamer가 소유한 카드 리스트를 출력한다
    // Gamer가 소유한 카드들의 목록을 보여주기 때문에 Gamer의 역할이다.
    @Override
    public void showCards() {
        StringBuilder sb = new StringBuilder();
        sb.append("현재 보유 카드 목록 \n");

        for(Card card : cards) {
            sb.append(card.toString());
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}
