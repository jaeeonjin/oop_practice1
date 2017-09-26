import java.util.Collections;
import java.util.Stack;


/**
 * Created by JaeeonJin on 2017-09-26.
 * - 52개의 카드를 생성하면 된다. (즉, 기존의 카드 끗 수 결정, 포인트 결정은 카드에서 해야할 일이다.)
 * - 랜덤으로 카드 한 장을 건넨다.
 * - Repactoring
 *  > List -> Stack (의미상 스택이 더 맞다. 카드를 pop..)
 *  > 매번 draw할 때마다 랜덤으로 뽑기 - 성능 상의 이슈 발생..
 *    따라서 Collections.shuffle을 이용해 미리 랜덤으로 만들어놓고 차례대로 뽑기로 바꿈.
 */
public class CardDeck {

    private static final int CARD_COUNT = 13;

    private Stack<Card> cards;

    public CardDeck() {
        cards = generateCards();
        Collections.shuffle(cards);
    }

    // 카드들을 생성한다.
    private Stack<Card> generateCards() {
        /*
        LinkedList를 사용하는 이유
        - ArrayList는 배열과 같다. 즉, 중간 원소를 삭제하고 남은 데이터들을 다시 복사한다. 성능 면에서 비효율적.
         */
        Stack<Card> cards = new Stack<>();

        for(Card.Pattern pattern : Card.Pattern.values()) {
            for(Card.Denomination denomination : Card.Denomination.values()) {
                Card card = new Card(pattern, denomination);
                cards.push(card);
            }
        }

        return cards;
    }

    /**
     * 카드 뽑기
     * - 남아 있는 카드 중 1개를 뽑는다.
     * - 뽑은 카드는 카드덱에서 제거한다.
     */
    public Card draw() {
       return this.cards.pop();
    }

    // 랜덤으로 카드를 한 장 얻어온다.
    private Card getRandomCard() {
        int size = cards.size();
        int select = (int) (Math.random()*size);
        return cards.get(select);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Card card : cards) {
            sb.append(card.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

}
