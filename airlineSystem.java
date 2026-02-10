import java.io.*;
import java.util.Scanner;
abstract class Flight{
    public String flightNo;
    public String departure;
    public String destination;
    public double price;
    public boolean [][] seats;

    public Flight(String flightNo,String departure,String destination,double price,int rows,int columns){
        this.flightNo=flightNo;
        this.departure=departure;
        this.destination=destination;
        this.price=price;
        this.seats=new boolean[rows][columns];
    }
    public String getFlightNo(){
       return flightNo;
    }
    public String getDepature(){
       return departure;
    }
    public String getDestination(){
        return destination;
    }  
    public double getPrice(){
        return price;
    }
    public boolean[][] getSeats(){
        return seats;
 }
 public abstract String getInfo();

public boolean BookSeat(int rows,int columns){
    if(rows>0 && rows<seats.length && columns>0 && columns<seats.length){
        if(!seats[rows][columns]){
        seats[rows][columns]=true;
        return true;
    }
}
    return false;
}
}

class DomesticFlight extends Flight {
    private String airline;

    public DomesticFlight(String flightNumber, String departure, String destination, double price, String airline, int rows, int columns) {
        super(flightNumber, departure, destination, price, rows, columns);
        this.airline = airline;
    }

    @Override
    public String getInfo() {
        return "Domestic Flight: " + flightNo+ ", Departure: " + departure + ", Destination: " + destination + ", Price: $" + price + ", Airline: " + airline;
    }
} 
class InternationalFlight extends Flight{
    private String airlinecode;


    public InternationalFlight(String flightNumber, String departure, String destination, double price, String airlinecode, int rows, int columns){
        super(flightNumber, departure, destination, price, rows, columns);
        this.airlinecode=airlinecode;
    }

    @Override
    public String getInfo(){
        return "International Flight:"+flightNo+",Departure:"+departure+", Destination"+destination+",Price"+price+",AirlineCode"+airlinecode;
    }

interface FlightData{
    void add(Flight flights);
    void displayAll();
    void deleteByFilghtNo(String flightNo);
    void searchFlightbyNumber(String flightNumber);
    void searchByDeparture(String departureCity);
    void savetoFile(File file) throws IOException;
    void showFlight(String flightNo);
}
class FlightManagement implements FlightData{
    private final int MAX_FLIGHTS=100;
    private  Flight[] flights;
    private int NumFlights;

    public FlightManagement(){
        flights=new Flight[MAX_FLIGHTS];
        NumFlights=0;
    }
    public void add(Flight flight){
        if(NumFlights<MAX_FLIGHTS){
            flights[NumFlights++]=flight;
        }
        else{
            System.out.println("Cannot add more flights");
        }
    }
    public void delete(String Flight ){
        for(int i=0;i<NumFlights;i++){
            if(flights[i].getFlightNo().equals(Flight)){
             for(int j=i;j<NumFlights-1;j++){
                flights[j]=flights[j+1]; 
             }
             flights[NumFlights-1]=null;
            NumFlights--;
            System.out.println("Flight deleted successfully");
            return;
            }
        }
        System.out.println("FLight no"+flights+"Not found");
    }
    public void displayAll(){
        String horizontalborder="+----------+-------------------+------------";
        String format= "| %-16s | %-16s | %-16s | $%-16.2f |%n";
        System.out.println(horizontalborder);
        System.out.println("| Flightnumber    | Depature   | Destination  | Price |\n");
        System.out.println(horizontalborder);
    
        for(int i=0;i<NumFlights;i++){
            Flight flight=flights[i];
            System.out.printf(format,flight.getFlightNo(),flight.getDepature(),flight.getDestination(),flight.getPrice());

        }
        System.out.println(horizontalborder);
    }
    public void searchFlightNumber(Flight flightnumber){
        for(int i=0;i<NumFlights;i++){
            if(flights[i].getFlightNo().equals(flightnumber)){
                System.out.println("Flight found");
                System.out.println(flights[i].getInfo());
                return;
            }
        }
        System.out.println("FlightNo"+flightnumber+"Not found");
    }
    public void searchByDeparture(String depaturecity){
        boolean found=false;
        for(int i=0;i<NumFlights;i++){
            if(flights[i].getDepature().equalsIgnoreCase(depaturecity)){
            if(!found){
                System.out.println("Flights departing from"+depaturecity+":");
                return;
            }
            System.out.println(flights[i].getInfo());
            }
        }
    }
  public void savetoFile(File file) throws IOException{
   try (PrintWriter writer=new PrintWriter(new FileWriter(file))){
     String horizontalborder="------------+------------+-----------------------";
     String format=" | %-16s | %-16s |%-16s |%-16f |%n";
     writer.println(horizontalborder);
    writer.println("| Flightnumber    | Depature   | Destination  | Price |\n");
        writer.println(horizontalborder);
    
        for(int i=0;i<NumFlights;i++){
            Flight flight=flights[i];
            System.out.printf(format,flight.getFlightNo(),flight.getDepature(),flight.getDestination(),flight.getPrice());

        }
        System.out.println(horizontalborder);

   }
  }
  public void BookFlight(String flightnumber){
    for(Flight flight:flights){
        if(flight.getFlightNo().equals(flightnumber)){
            System.out.println("Seats for flight"+flightnumber);
        }
        boolean [][] seats=flight.getSeats();
        for(int i=0;i<seats.length;i++){
            for(int j=0;j<seats.length-1;j++){
             int seatNumber=(i*seats[0].length)+(j=1);
             if(seats[i][j])
                System.out.println("B");
             }
             System.out.println(seatNumber+" ");
            }
     }
     System.out.println("Enter the seat you want to booked");
     Scanner sc=new Scanner(System.in);
     int seatNumber=sc.nextInt();
     sc.nextLine();
    int rows = (seatNumber-1)/seats[0].length;
    int columns= (seatNumber-1)%seats[0].length;
if(rows>0 && rows<=seats.length && columns>0 && columns<seats.length){
    if(flight.BookSeat(rows,columns)){
     System.out.println("Successfully Booked");
    }
    else{
    System.out.println("Invalid seatno");
}

  }
  return;

}
}
  System.out.println("Flight"+flightNumber+"not found");

public class airlineSystem{
    static Scanner sc=new Scanner(System.in);
    static FlightData flightdata=new FlightManagement();
    static File file=new File("airlin.txt");


    public static void main(String[] args) {

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            System.out.println("\nAirline Management System");
            System.out.println("1. Add Flight");
            System.out.println("2. Delete Flight");
            System.out.println("3. Display All Flights");
            System.out.println("4. Search Flight by Flight Number");
            System.out.println("5. Search Flight by Departure City");
            System.out.println("6. Save Flights to File");
            System.out.println("7. Book a Flight");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice= sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    add();
                    break;
                case 2:
                    deleteByFilghtNo();
                    break;
                case 3:
                    displayAllFlights();
                    break;
                case 4:
                    searchFlightByFlightNumber();
                    break;
                case 5:
                    searchFlightByDepartureCity();
                    break;
                case 6:
                    saveFlight();
                    break;
                case 7:
                    bookFlight();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 8.");
            }
        }
    }
public void addFlight(){
    System.out.println("Add Flight");
    System.out.println("Enter departure city");
    String depature=sc.nextLine();
    System.out.println("Enter destination");
    String destination=sc.nextLine();
    System.out.println("Enter price");
    double price=sc.nextDouble();
    System.out.println("Enter rows");
    int rows=sc.nextInt();
    System.out.println("Enter columns");
    int columns=sc.nextInt();
    String flightnumber=generateFlightNumber();
    System.out.println("Flight number is" + flightnumber);
    System.out.println("Choose whter its a domestic or international flight??");
    String type=sc.nextLine();
    System.out.println("");
    if(type.equalsIgnoreCase("domestic")){
        flightData.add(new DomesticFlight(flightnumber, flightnumber, destination, price, type, rows, columns));
    }
    else if (type.equalsIgnoreCase("International")){
        flightData.add(new InternationalFlight(flightnumber, flightnumber, destination, price, type, rows, columns))
    }
    else{
        System.out.println("No type");
    }
 static void deleteFlight(){
    System.out.println("Enter flightnumber to delete");
    String flightnumber=sc.next();
    flightdata.deleteByFilghtNo(flightnumber);
 }
 static void displayFlight(){
    System.out.println("Display all flights");
    flightdata.displayAll();
 }
 static void SearchFlight(){
    System.out.println("Search flight by flight no");
    String flightno=sc.next();
    flightdata.searchFlightbyNumber(flightno);
 }
 static void SerachDeparture(){
    System.out.println("Serach flight by departure");
    String departure=sc.nextLine();
    flightdata.searchByDeparture(departure);
 }
 static void SaveFlightFile(){
    System.out.println("Save the flight to file");
    try{
     flightdata.savetoFile(file);
     System.out.println("Saved successfully");
    }
    catch (IOException e){
        System.out.println("e.getMessage(");
    }
   
 }
   static void bookFlight() {
        System.out.print("\nEnter Flight Number to book: ");
        String flightNumber = sc.nextLine();
        flightdata.bookFlight(flightNumber);
    }
 
}

}



