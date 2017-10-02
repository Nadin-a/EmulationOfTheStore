package testtaskshop;

/**
 *
 * @author Nadina Class for non-alcoholic and alcoholic beverages. Fields
 * "composition" and "strength of the drink" are combined in the "information"
 * field.
 */
enum Type {
    WINE, SPIRITS, BEER, LIQUEURS, MINERAL_WATER, JUICES, OTHER_DRINKS
};

class Drink {

    private String name;
    private double standart_cost;
    private double cost_with_markup;
    public Type type = Type.OTHER_DRINKS;
    private float volume;
    private String information;
    private int amount;
    private int current_markup;
    private int how_much_is_sold;
    private int how_many_times_is_bought;
    private boolean isOver = false;

    public Drink() {
        this.how_much_is_sold = 0;
        this.how_many_times_is_bought = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStandart_cost() {
        return standart_cost;
    }

    public void setStandart_cost(double standart_cost) {
        this.standart_cost = standart_cost;
    }

    public double getCost_with_markup() {
        return cost_with_markup;
    }

    public void setCost_with_markup(double cost_with_markup) {
        this.cost_with_markup = cost_with_markup;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCurrent_markup() {
        return current_markup;
    }

    public void setCurrent_markup(int current_markup) {
        this.current_markup = current_markup;
    }

    public int getHow_much_is_sold() {
        return how_much_is_sold;
    }

    public void setHow_much_is_sold(int how_much_is_sold) {
        this.how_much_is_sold = how_much_is_sold;
    }

    public int getHow_many_times_is_bought() {
        return how_many_times_is_bought;
    }

    public void setHow_many_times_is_bought(int how_many_times_is_bought) {
        this.how_many_times_is_bought = how_many_times_is_bought;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    
    
    /**
     * Calculation cost with markup 
     */
    public void mark_up(int markup) {
        this.current_markup = markup;
        double percent = this.getStandart_cost() / 100 * markup;
        double stCost = this.getStandart_cost();
        this.cost_with_markup = stCost + percent;
    }

    @Override
    public String toString() {
        return "Drink{" + "name=" + name + ", standart_cost=" + standart_cost + ", cost_with_markup=" + cost_with_markup + ", type=" + type + ", volume=" + volume + ", information=" + information + ", amount=" + amount + ", current_markup=" + current_markup + ", how_much_is_sold=" + how_much_is_sold + ", how_many_times_is_bought=" + how_many_times_is_bought + '}';
    }

}
