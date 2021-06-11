import java.util.*;

class vehicle{
    String type;
    Date startTime;
    vehicle(String type){
        this.type=type;
        startTime=new Date();cl
    }
}
public class parkingSpace {
    Map<String,vehicle> vehicleMap=new HashMap<String,vehicle>();
    Scanner scan=new Scanner(System.in);
    final int totalCarSpace=300;
    final int totalBikeSpace=200;
    int carSpaceOccupied=0;
    int bikeSpaceOccupied=0;
    float totalCollection=0;
    enum vehicleAllowed{car,bike}

    void park(String type){
        try{
            vehicleAllowed.valueOf(type);
        }catch(IllegalArgumentException e){
            System.out.println("You can't park this vehicle");
            return;
        }
        if(spaceLeft(type)){
            System.out.println("No space available bro man");
            return;
        }

        System.out.println("Space available, Please enter licence plate ");
        String licencePlateNo=scan.nextLine();
        vehicleMap.put(licencePlateNo,new vehicle(type));
        System.out.println("Hola, vehicle parked,have a nice day");
        if(type.equals("car")){
            carSpaceOccupied+=1;
        }else bikeSpaceOccupied+=1;

    }
    int timeDifference(Date exitTime,Date entryTime){
        final float MILLI_TO_HOUR = 1000 * 60 * 60;
        float temp=(exitTime.getTime() - entryTime.getTime()) / MILLI_TO_HOUR;
        return (int) Math.ceil(temp);
    }
    void unPark(String licencePlateNo){

        if(!vehicleMap.containsKey(licencePlateNo)){
            System.out.println("No such vehicle found");
            return;
        }else{
            vehicle veh=vehicleMap.get(licencePlateNo);
            Date exitTime=new Date();
            int hours=timeDifference(exitTime,veh.startTime);
            System.out.println(hours);
            float price=0;
            if(veh.type.equals("car")){
                price=40*hours;
            }else{
                price=20*hours;
            }
            System.out.println("Do you have movie ticket?\n 1.Yes 2.No");
            if(scan.nextLine().equals("1")){
                price= (float) (0.90*price);
            }else{
                System.out.println("Invalid choice");
                return;
            }
            totalCollection=totalCollection+price;
            System.out.println("Please pay "+price+" @ counter");
            vehicleMap.remove(licencePlateNo);
            if(veh.type.equals("car")){
                carSpaceOccupied-=1;
            }else{
                bikeSpaceOccupied-=1;
            }
        }

    }
    boolean spaceLeft(String type){
        if(type.equals("car") && totalCarSpace<=carSpaceOccupied) return true;
        else if(type.equals("bike") && totalBikeSpace<=bikeSpaceOccupied) return true;
        else return false;
    }
    void printMap(){
        Iterator<Map.Entry<String,vehicle>> itr= vehicleMap.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<String,vehicle> entry=itr.next();
            System.out.println("Key->"+entry.getKey());
            vehicle obj=entry.getValue();
            System.out.println("Type->"+obj.type+" Entry time->"+obj.startTime);
        }
    }
    void totalCollection(){
        System.out.println("Collection till now->"+totalCollection);
    }
    void totalSpaceOccupied(){
        System.out.println("Total car space occupied->"+carSpaceOccupied);
        System.out.println("Total bike space occupied->"+bikeSpaceOccupied);
    }
    public static void main(String [] args){
        Scanner scan=new Scanner(System.in);
        parkingSpace space=new parkingSpace();

        int option=0;
        String type;
        while(true){
            System.out.println("1.Park 2.Unpark 3.Occupied Space 4. vehicle logs");
            option=scan.nextInt();
            scan.nextLine();
            switch(option){
                case 1: System.out.println("Enter vehicle type");
                        space.park(scan.nextLine());
                        break;
                case 4: space.printMap();
                        break;
                case 2: System.out.println("Enter Licence plate number");
                        space.unPark(scan.nextLine());
                        break;
                case 3: space.totalSpaceOccupied();
                        break;
                case 5: space.totalCollection();
                    break;
                default: System.out.println("Invalid selection");

            }
        }
    }
}
