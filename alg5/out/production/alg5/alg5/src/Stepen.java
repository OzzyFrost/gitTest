public class Stepen {
    public static void main(String[] args) {
        System.out.println("5^4 = "+step(5,4));
        System.out.println("-3^5 = "+step(-3,5));
    }
    public static int step(int osn,int pokaz){
        if (pokaz<0){
            throw new NumberFormatException();
        }
        if (pokaz == 0){
            return 1;
        }
        return osn*step(osn,pokaz-1);

    }

}
