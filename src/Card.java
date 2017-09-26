/**
 * Created by JaeeonJin on 2017-09-26.
 * - Repactoring
 *  > Pattern, Denomination 에 대한 Enum 처리
 */
public class Card {
    private Pattern pattern; // 무늬
    private Denomination denomination; // 끗수(A, 2~10, J, Q, K)
    private int point; // 카드의 포인트


    // 생성자의 사용 이유 : 필요한 인자(패턴, 끗수)를 강제할 수 있다.
    public Card(Pattern pattern, Denomination denomination) {
        this.pattern = pattern;
        this.denomination = denomination;
    }

    // 카드의 끗수 별로 점수를 부여하기 위한 메소드
    private int numberToPoint(int number) {
        if( number >= 11 ) return 10;
        return number;
    }

    @Override
    public String toString() {
        return "CARD : {"+"pattern=" + pattern.value + ", denomination=" + denomination.mark + "}";
    }

    public int getPoint() {
        return point;
    }

    // Enum을 사용하는 이유 : 가독성, 코드 단순, 인스턴스 생성과 상속의 방지, 열거의 의미를 분명하게 표현
    // 패턴에 대한 내부 Enum 클래스 선언
    public enum Pattern {
        SPADE("spade"),
        HEART("heart"),
        DIAMOND("diamond"),
        CLUB("club");

        private String value;

        Pattern() {}

        Pattern(String value) {
            this.value = value;
        }
    }

    // 끗 수에 대한 ENUM 클래스 선언
    public enum Denomination {
        ACE("A", 1),
        TWO("2", 2),
        THREE("3", 3),
        FOUR("4", 4),
        FIVE("5", 5),
        SIX("6", 6),
        SEVEN("7", 7),
        EIGHT("8", 8),
        NINE("9", 9),
        TEN("10", 10),
        JACK("J", 10),
        QUEEN("Q", 10),
        KING("K", 10);

        private String mark;
        private int point;

        Denomination() {
        }

        Denomination(String mark, int point) {
            this.mark = mark;
            this.point = point;
        }

        public int getPoint() {
            return this.point;
        }
    }
}
