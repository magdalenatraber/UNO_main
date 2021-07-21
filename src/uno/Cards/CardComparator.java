package uno.Cards;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {


    @Override
    public int compare(Card o1, Card o2) {
        if (o1.getColor().compareTo(o2.getColor()) == 0){
          if(o1.getType().getCaption().equals("+4") && o2.getType().getCaption().equals("W") ||o2.getType().getCaption().equals("+4") && o1.getType().getCaption().equals("W"))
          return o1.getType().getCaption().compareTo(o2.getType().getCaption()) * -1;
          else
               return o1.getType().getCaption().compareTo(o2.getType().getCaption()); }

        else if((o1.getColor().getCaption().equals("W") && o2.getColor().getCaption().equals("Y")) || o2.getColor().getCaption().equals("W") && o1.getColor().getCaption().equals("Y") )
           return o1.getColor().getCaption().compareTo(o2.getColor().getCaption()) * -1;
        else
            return o1.getColor().getCaption().compareTo(o2.getColor().getCaption());
    }
}