
/**
 * The Application class uses a carpark instance to run the application.
 * It contains different methods to maintain the functionality of the application for the user.
 * Each option in the starting menu and mainmenu has a single purpose method.
 * It also has a main method to run the application.
 *
 * @author Hamza Suhail
 * @version 11.0.2, Date 11/04/2021, Student id: 102666611
 */

import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.Random;
import java.lang.*;
public class Application
{
    private CarPark carpark;                    // instance of carpark
    private DateTimeFormatter datetime;        // formatter of datetime
    
    /**
     * Constructor for Application
     * @param CarPark carpark, instance of a carpark
     */
    public Application(CarPark carpark)
    {
        this.carpark = carpark;
        datetime = DateTimeFormatter.ofPattern("hh:mm a");
    }
    
    /**
     * Main Menu being displayed to the user.
     * 
     * @return IO.getText(), The input from user in the main menu
     */
    
    public String mainMenu()
    {
        IO.println("************************");
        IO.println("1. List all car slots");
        IO.println("2. Park a Car");
        IO.println("3. Find a Car");
        IO.println("4. Remove a Car");
        IO.println("5. Add a car slot");
        IO.println("6. Delete a car slot");
        IO.println("7. Exit");
        IO.println("************************");
        return IO.getText("What would you like to do?").trim();
    }
    
    /**
     * The main application being run with different methods.
     * It has the sub menu where carpark is established for staff and visitors
     * It also has a main menu where user can select options based on choice 1-7.
     * 
     */
    
    
    public void runApplication()
    {   
        boolean exitMenu=false;
        boolean startingMenu=false;
        while(exitMenu!=true)
        {   
            while(startingMenu!=true){
                IO.println("Start to establish Car Park for staff and visitors");
                addvisitorParkingSlots();
                addstaffParkingSlots();
                startingMenu=true;
            }
            switch(mainMenu())
            {
                case "1":
                showAllParkingSlots();
                break;
                case "2":
                parkVehicle();
                break;
                case "3":
                findVehicle();
                break;
                case "4":
                removeVehicle();
                break;
                case "5":
                addAParkingSlot();
                break;
                case "6":
                deleteSpot();
                break;
                case "7":
                exitMenu=true;
                IO.println("Thank you for using me.");
                break;
                
                default:IO.println("\n***\nPlease enter a valid choice!\n***\n");
            }   
        }
    }
    
    /**
     * Adding a new parkingslot to established parkingslots.
     * User can add staff or visitor parkingslot
     * 
     */
    
    public void addAParkingSlot()
    {
        /* ask user input for id of parkingslot and ask if it is staff or visitor parking */
        String id = IO.getText("Enter id of ParkingSlot:");
        String staff = IO.getText("Is this a staff or visitor parking?:").toUpperCase();
        boolean isParkingSlotDuplicate = false;
        if (id.equals("none"))
        {
            IO.println("Error: do not leave the id blank!");
        }
        else
        {
            for(ParkingSlot aParkingSlot : carpark.getAllParkingSlots())
            {
                /* checking if id already exists */
                if(aParkingSlot.getParkingSlotId().equalsIgnoreCase(id))
                {
                    isParkingSlotDuplicate = true;
                    IO.println("\n****************\nError: Parking Spot "+id+" already exists!\n****************\n");
                }
            }
            
            if(!isParkingSlotDuplicate)
            {
                /* if user chooses staff parking */
                if(staff.contains("STAFF"))
                {
                    /* checking if id starts with S, total length is 4 and contains numerics after S */
                    if (id.startsWith("S") && id.length()==4 && id.substring(1).chars().allMatch( Character::isDigit ))
                    {
                        IO.println("\n****************\nSuccess! Staff Parking Added.\n****************\n");
                        ParkingSlot aParkingSlot = new ParkingSlot(id, true);         // new parking slot with isStaff as true
                        carpark.addNewParkingSlot(aParkingSlot);
                        showAllParkingSlots();
                    }
                    else
                    {
                        IO.println("\n****************\nStaff Parking must start with S and be 4 digits eg. (S110). Try again.\n****************\n");
                    }
                }
                 /* if user chooses visitor parking */
                else if(staff.contains("VISITOR"))
                {
                    /* checking if id starts with V, total length is 4 and contains numerics after V */
                    if (id.startsWith("V")  && id.length()==4 && id.substring(1).chars().allMatch( Character::isDigit ))
                    {
                        IO.println("\n****************\nSuccess! Visitor Parking Added.\n****************\n");
                        ParkingSlot aParkingSlot = new ParkingSlot(id);       // new parking spot by default isStaff is false
                        carpark.addNewParkingSlot(aParkingSlot);
                        showAllParkingSlots();
                    }
                    else
                    {
                        IO.println("\n****************\nVisitor Parking must start with V should be 4 digits eg. (V999). Try again.\n****************\n");
                    }
                }
                /* if user doesn't choose staff or visitor. Display an error */
                else
                {
                    IO.println("\n****************\nPlease choose from \"staff\" or \"visitor\"\n****************\n");
                }
            }
        }
    }
    
    
    /**
     * Displaying all of the existing parkingslots in the carpark to the user.
     * 
     */
    
    
    public void showAllParkingSlots()
    {
        IO.println(String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s","ParkingSlot Id","Belongs to Staff", "Occupied Status","Arrival Time","Arrival Date","Time In","Registration Number" ));
        /*  for loop and get all parking slots in the carpark */
        for(ParkingSlot aParkingSlot : carpark.getAllParkingSlots())
        {
            /*  if car is parked show details of the car */
            if(aParkingSlot.getVehicle()!=null && aParkingSlot.getIsOccupied() && aParkingSlot.getVehicle() instanceof Car )
            {
                IO.println(String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s",aParkingSlot.getParkingSlotId(),"" +((aParkingSlot.getIsStaff())?"Staff Parking":"Visitor Parking"),""+((aParkingSlot.getIsOccupied())?"Occupied":"Vacant"),aParkingSlot.getArrivalTime().format(datetime),aParkingSlot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aParkingSlot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aParkingSlot.getArrivalTime().getDayOfMonth()+", "+aParkingSlot.getArrivalTime().getYear(),aParkingSlot.getVehicle().getTimeIn()+" min.",((Car)aParkingSlot.getVehicle()).getrego() ));
            }
            /*  else display id, staff or visitor parking, occupied status and "-" for vehicle details */
            else
            {
                IO.println(String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s",aParkingSlot.getParkingSlotId(),((aParkingSlot.getIsStaff())?"Staff Parking":"Visitor Parking"),""+((aParkingSlot.getIsOccupied())?"Occupied":"Vacant"), "---", "---", "---", "---"));
            }
        }
    }
    
    
    /**
     * Parking the vehicle for the user.
     * 
     */
    
    public void parkVehicle()
    {
        ArrayList<ParkingSlot> receivedAvailParkingSlots = new ArrayList<ParkingSlot>();
        String regNum = IO.getText("Enter registration number of the vehicle:");
        /*  for checking if rego contains numbers after first digit */
        String digRegNum= regNum;
        digRegNum = digRegNum.substring(1);
        /*  if it contains digits after first character of registration number then true else false */
        boolean isNumeric = digRegNum.chars().allMatch( Character::isDigit );
        /*  we check if first if first character is uppercase */
        boolean firstChar = Character.isUpperCase(regNum.charAt(0));
        if(!regNum.equals("none"))
        {
            if (regNum.length()==6 && isNumeric && !carpark.findVehicleRego(regNum).contains(regNum) && firstChar)
            {
                String ownerName =  IO.getText("Enter name of owner:");
                if(ownerName.matches(".*\\d.*"))                                  // checking if owner name has digits
                {
                  IO.println("**********\nOwner name shouldn't contain any digits eg.(\"Hamza\") \n**********\n");

                }
                else if (ownerName.equals("none"))                                 // checking if input is none
                {
                IO.println("**********\nOwner can't be none \n**********\n");

                }
                
                else
                {
                    Car car = new Car(regNum , ownerName);                          // init new car
                    Vehicle vehicle = car;                                          // assign car to vehicle
                    receivedAvailParkingSlots = carpark.getAllParkingSlots();       // get all parking slots
                    if(receivedAvailParkingSlots.isEmpty())                         // checking if none parking slots
                    {
                        IO.println("**********\nAlert: No ParkingSlots available! \n**********\n");
                    }
                    else if(!carpark.findVehicleRego(regNum).isEmpty())              // checking if rego already exists
                    {
                        IO.println("**********\nAlert: Vehicle Registration Already exists! Can't Park more than one Vehicle. \n**********\n");
                    }
                    else if(!carpark.findVehicleOwner(ownerName).isEmpty())          // checking if owner already exists
                    {
                        IO.println("**********\nAlert: Vehicle Owner Already exists! Can't Park more than one Vehicle. \n**********\n");
                    }
                    else
                    {
                        /*  display list of parking slots */
                        IO.println("List of ParkingSlots:\n");
                        IO.println(String.format("%-20s%-20s%-20s","ID", "Type of Parking", "Vacany Status" )); //headers
                        for(ParkingSlot aParkingSlot : receivedAvailParkingSlots)
                          {
                              // display id, isStaff parking, if occupied
                             IO.println(String.format("%-20s%-20s%-20s",aParkingSlot.getParkingSlotId(),((aParkingSlot.getIsStaff())?"Staff Parking":"Visitor Parking"), ((aParkingSlot.getIsOccupied())?"Occupied":"Vacant") ));
                          }
                         /*  User choosing parking ParkingSlot */
                        String ParkingSlotId = IO.getText("choose a parking ParkingSlot:\n");
                        if(carpark.findParkingbyID(ParkingSlotId).getParkingSlotId().equals(ParkingSlotId) && !carpark.findParkingbyID(ParkingSlotId).getIsOccupied() )
                        {
                            /*  asking user if staff or visitor */
                            String Staffmember = IO.getText("Are you a staff member or visitor?").toUpperCase();
                            switch(Staffmember)
                            {
                                /*  if user inputs staff */
                                case "STAFF":
                                case "STAFFMEMBER":
                                case "STAFF MEMBER":
                                /*  checking if the parking is a staff parking */
                                if(carpark.findParkingbyID(ParkingSlotId).getIsStaff())
                                {
                                // add staff vehicle
                                carpark.findParkingbyID(ParkingSlotId).addStaffVehicle(vehicle);
                                IO.println("**********\nSuccess: Staff Vehicle has been Parked Successfully! \n**********\n");
                                showAllParkingSlots();
                                break;
                                }
                                /*  display error to inform user that it is not staff parking */
                                else
                                {
                                    IO.println("**********\nThe spot you have chosen is not a staff parking spot \n**********\n");
                                    break;
                                }
                                /*  case user inputs visitor */
                                case "VISITOR":
                                case "VIS":
                                /*  checking if its a visitor parking slot */ 
                                if(!carpark.findParkingbyID(ParkingSlotId).getIsStaff())
                                {
                                    // add visitor vehicle
                                    carpark.findParkingbyID(ParkingSlotId).addVisitorVehicle(vehicle);
                                    IO.println("**********\nSuccess: Visitor Vehicle has been Parked Successfully! \n**********\n");
                                    showAllParkingSlots();
                                    break;
                                }
                                /*  display error to inform user that it is not visitor parking */
                                else
                                {                                          
                                    IO.println("**********\nThe spot you have chosen is not a visitor parking spot\n**********\n");
                                    break;
                                }
                                /* inform user to choose from staff or visitor input */ 
                                default:
                                IO.println("**********\nPlease choose from \"staff\" or \"visitor\"! \n**********\n");
                                break;
                                
                            }
                        }
                        /*  checking to see if parking slot is occupied  */
                        else if (carpark.findParkingbyID(ParkingSlotId).getParkingSlotId().equals(ParkingSlotId) && carpark.findParkingbyID(ParkingSlotId).getIsOccupied())
                        {
                            IO.println("**********\nError: This Parking Spot is occupied! \n**********\n");
                        }
                        /*  if parking not in list of parkings */
                        else
                        {
                            IO.println("**********\nError: Enter a valid Parking Spot!\n**********\n");
                        }
                    }
                }
            }
            /*  if registration doesn't start with capital letter */
            else if (!firstChar)
            {
                IO.println("**********\nAlert:Registration has to start with a capital letter eg. (P48999) \n**********\n");
            }
            /*  if registration doesn't contain numerics after first digit */
            else if (!isNumeric)
            {
                IO.println("**********\nAlert:Registration has to contain numbers after first letter eg. (J48999) \n**********\n");
            }
            /*  if total registration length is not 6 characters */
            else if (regNum.length()!=6)
            {
                IO.println("**********\nAlert:Registration has to be total 6 characters in length eg. (L98999) \n**********\n");
            }
        }
        /*  if registration is none */
        else
        {
            IO.println("**********\nAlert:Registration Number can't be none \n**********\n");
        }
        
    }

    /**
     * Adding visitor parkingslots with a sequential id
     * 
     */               
    
    public void addvisitorParkingSlots()
    {
        boolean isQuery = false;                                                            //initial query is false for a while loop
        while(!isQuery)
        {
            String nRows = IO.getText("Enter the number of visitor ParkingSlots").trim();       //user input for n rows
            String visitorid = "V";                                                             // identifier of visitor id
            boolean isNumeric = nRows.chars().allMatch( Character::isDigit );                   // checking for numeric input
        
            if (nRows.equals("0") || nRows.isEmpty() || nRows.equals("none") || !isNumeric)
            {
                IO.println("Error: Enter atleast 1 row");
            }
            else
            {
                int i = 0;
                int rows=Integer.parseInt(nRows);                                              //parsing string input to int
            
                while (i<rows)
                {
                    i++;                                                                       //increment i
                    String uniqueNum = String.format("%03d", i);                               // 3 digit sequential id "001", "002" etc
                    ParkingSlot aParkingSlot = new ParkingSlot(visitorid + uniqueNum);         // V + 001, V+ 002 and so on
                    carpark.addNewParkingSlot(aParkingSlot);                                   // add slot to carpark
            
                }
                IO.println(+i+ "\tVisitor Records Successfully Created");
                isQuery = true;                                                                //set initial query to true
            
            }
        }
    }
    
    /**
     * Delete the parkingspot if it isn't occupied and no vehicle is parked
     * 
     */
    
    public void deleteSpot()
    {
        ArrayList<ParkingSlot> receivedAllParkingSlots = new ArrayList<ParkingSlot>();    // new array list of parkingslots in carpark
        receivedAllParkingSlots = carpark.getAllParkingSlots();
        IO.println("List of all Parking Spots:\n");
        IO.println(String.format("%-20s%-20s%-20s","ID", "Type of Parking", "Vacany Status" ));
        for(ParkingSlot aParkingSlot : receivedAllParkingSlots)                            // display all parking slots
        {
         IO.println(String.format("%-20s%-20s%-20s",aParkingSlot.getParkingSlotId(),((aParkingSlot.getIsStaff())?"Staff Parking":"Visitor Parking"), ((aParkingSlot.getIsOccupied())?"Occupied":"Vacant") ));
        }
        String tempId = IO.getText("Enter the parking Spot you want to delete:");          // get spot id from user
        /* If the spot isn't occupied and
         * vehicle isn't parked
         * then print success else error
         */
        if(carpark.removeAParkingSlot(tempId) )
        {   
            IO.println("Success: spot deleted!");
        }
        else
        {
            IO.println("Error: Spot doesn't exist or is currently occupied");
        }
            
    }
    
    
    /**
     * Adding staff parking spots with a sequential id.
     * 
     */
    
    public void addstaffParkingSlots()
    {
        boolean isQuery = false;                                                             //initial query is false for a while loop
        while(!isQuery)
        {
            String id = IO.getText("Enter the number of staff ParkingSlots").trim();          //user input for n rows
            String staffid = "S";                                                             //unique identifier for staff parking
            boolean isNumeric = id.chars().allMatch( Character::isDigit );                    // checking for numeric input
            if (id.equals("0") || id.isEmpty() || id.equals("none") || !isNumeric)
            {
                IO.println("Error: Enter atleast 1 row");
            }
            else
            {
                int x = 0;
                int i=Integer.parseInt(id);                                                   //parsing string input to int
                while (x<i)                                                                   //while 0<n rows
                {
                    x++;                                                                       //increment x
                    String uniqueNum = String.format("%03d", x);                               // 3 digit sequential id "001", "002" etc
                    ParkingSlot aParkingSlot = new ParkingSlot(staffid + uniqueNum, true);     //S+ 001, S+ 002 and set isStaff to true
                    carpark.addNewParkingSlot(aParkingSlot);                                   //add parking slot to carpark
                
                }
                IO.println(+x+ "\tStaff Records Successfully created");
                isQuery= true;                                                                  //set initial query to true
            }
        }
    }
    
    /**
     * Remove the Vehicle based on the registration number
     * 
     */


    
    public void removeVehicle()
    { 
       String regoNumber = IO.getText("Enter your registration number:").toUpperCase(); //user input rego for car
       ArrayList<ParkingSlot> receivedAllParkingSlots = new ArrayList<ParkingSlot>(); 
       receivedAllParkingSlots = carpark.getAllParkingSlots();                          //get all carpark parkingslots
       boolean userQuery =  false;                                                      //initial user enquiry
       IO.println("Your Parking Details:\n");                                           // display parking details header
       IO.println(String.format("%-20s%-20s%-20s%-20s%-20s%-20s","ID", "Type of Parking", "Vacany Status", "Rego", "Owner", "Time In" ));
       for(ParkingSlot aParkingSlot : receivedAllParkingSlots)
        {
            /*  checking if parking is occupied and vehicle is an instance of Car */
            if(aParkingSlot.getIsOccupied() && aParkingSlot.getVehicle() instanceof Car )
            {
                 /*  checking if registration matches. */
                if(((Car)aParkingSlot.getVehicle()).getrego().equals(regoNumber))
                {
                 IO.println(String.format("%-20s%-20s%-20s%-20s%-20s%-20s",aParkingSlot.getParkingSlotId(),((aParkingSlot.getIsStaff())?"Staff Parking":"Visitor Parking"), ((aParkingSlot.getIsOccupied())?"Occupied":"Vacant"), ((Car)aParkingSlot.getVehicle()).getrego(),aParkingSlot.getVehicle().getOwner(), aParkingSlot.getVehicle().getTimeIn()+" min."  ));
                 aParkingSlot.removeVehicle();
                 userQuery = true;
                 IO.println("**********\nCar successfully removed!\n**********");
                }
            }
        }
         /*  if user query isn't true, display an error */
       if(!userQuery)
        {
           IO.println(String.format("%-20s%-20s%-20s%-20s%-20s%-20s","-","-", "-", "-","-", "-" ));
           IO.println("**********\nCar not found!\n**********");
        }
    }
    
    /**
     * Find Vehicle in the parking spot based on owner name or registration number.
     * 
     */
    
    public void findVehicle()
    {
        /*  user input for if they want to find car by rego or owner name */
       String initialEnquiry = IO.getText("Do you want to find vehicle by owner or registration?").toUpperCase();
       boolean userQuery =  false;                                                  // initial enquiry is false
       switch(initialEnquiry)
       {
           /*  case name, ownername or owner */
           case "NAME":
           case "OWNERNAME":
           case "OWNER":
           String ownerName = IO.getText("Enter the owner name:");
           ArrayList<ParkingSlot> receivedAllParkingSlots = new ArrayList<ParkingSlot>();
           receivedAllParkingSlots = carpark.getAllParkingSlots();                   // new arraylist of parking slots
           IO.println("Your Parking Details:\n");
           IO.println(String.format("%-20s%-20s%-20s%-20s%-20s","ID", "Type of Parking", "Time In Parking", "Rego", "Owner" ));
           for(ParkingSlot aParkingSlot : receivedAllParkingSlots)
            {
                 /*  if parking is occupied and vehicle is an instance of a car */
                if(aParkingSlot.getIsOccupied() && aParkingSlot.getVehicle() instanceof Car )
                {
                    /*  checking if owner name matches for vehicle parked */ 
                    if(aParkingSlot.getVehicle().getOwner().equalsIgnoreCase(ownerName))
                    {
                     IO.println(String.format("%-20s%-20s%-20s%-20s%-20s",aParkingSlot.getParkingSlotId(),((aParkingSlot.getIsStaff())?"Staff Parking":"Visitor Parking"),((Car)aParkingSlot.getVehicle()).getTimeIn() +" min.", ((Car)aParkingSlot.getVehicle()).getrego(),aParkingSlot.getVehicle().getOwner()  ));
                     userQuery = true;
                    }
                }
            }
            /*   if can't find an owner from parked car then display an error */ 
            if(!userQuery)
            {
            IO.println(String.format("%-20s%-20s%-20s%-20s%-20s","-","-", "-", "-","-" ));
            IO.println("Error: No owner Found");
            }
            break;
            /*   case if user wants to find car by registration or rego */ 
            case "REGISTRATION":
            case "REGO":
            String regoNumber = IO.getText("Enter your registration number:").toUpperCase();     //user input rego
            IO.println("Your Parking Details:\n");
            IO.println(String.format("%-20s%-20s%-20s%-20s%-20s","ID", "Type of Parking", "Time In Parking", "Rego", "Owner" ));
           for(ParkingSlot aParkingSlot : carpark.getOccupiedParkingSlots())                    // get occupied slots in carpark
           {
             /*   if the slot is occupied and vehicle is an instance of car */    
             if(aParkingSlot.getIsOccupied() && aParkingSlot.getVehicle() instanceof Car )
             {
                  /*   checking if rego matches the vehicle parked */ 
                 if(((Car)aParkingSlot.getVehicle()).getrego().equals(regoNumber))
                 {
                  IO.println(String.format("%-20s%-20s%-20s%-20s%-20s",aParkingSlot.getParkingSlotId(),((aParkingSlot.getIsStaff())?"Staff Parking":"Visitor Parking"), ((Car)aParkingSlot.getVehicle()).getTimeIn() +" min.", ((Car)aParkingSlot.getVehicle()).getrego(),aParkingSlot.getVehicle().getOwner()  ));
                  userQuery = true;
                }
             }
           }
           /*   if rego doesn't match then display an error */  
            if(!userQuery){
            IO.println(String.format("%-20s%-20s%-20s%-20s%-20s","-","-", "-", "-","-" ));
            IO.println("Error: No registration found");
            }
             break;
             /*   case if user doesn't enter owner or rego */   
             default:
             IO.println("Please choose from \"name\" or \"rego\"");
             break;
        }
    }
    
    /**
     * Initiating the the carpark and the application.
     * 
     */
    
    public static void main(String args[])
    {
       //creating new carpark 
       CarPark carpark = new CarPark();
       //executing the app
       Application app = new Application(carpark);
       IO.println("********************************************************\n-----------Swinburne Car Park System-----------------\n********************************************************"); 
       app.runApplication();
        
    }
    


}
