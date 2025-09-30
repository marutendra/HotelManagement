
import java.util.Scanner;
import java.sql.*;


public class HotelManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/mydb1";
        String username = "root";
        String password = "@UP33am6572";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
try (Connection cn = DriverManager.getConnection(url,username,password)){
    System.out.println("WELCOME TO GOLA GOLA RESORT");
    while(true) {
        System.out.println("1. New reservation ");
        System.out.println("2. check reservation ");
        System.out.println("3. Get room number ");
        System.out.println("4. Update reservation  ");
        System.out.println("5. delete reservation ");
        System.out.println("6. Exit ");
        int userInput = sc.nextInt();
        switch (userInput) {
            case 1:
                newReservation(cn, sc);
                break;
            case 2:
                checkreservation(cn, sc);
                break;
            case 3:
                getroomnumber(cn, sc);
                break;
            case 4:
                updateroomnumber(cn, sc);
                break;
            case 5:
                deletereservation(cn, sc);
                break;
            case 6:
                exit();
                sc.close();
                return;

            default:
                System.out.println(" Something went Wrong . try again !!!");
        }
    }
} catch (RuntimeException | SQLException e) {
    throw new RuntimeException(e);
}


    }

    private static void newReservation(Connection cn, Scanner sc) {
        try {
            System.out.println("registration_id : ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.println(" Name : ");
            String name = sc.nextLine();
            System.out.println(" Age :");
            int age = sc.nextInt();
            System.out.println(" enter room number : ");
            int room=sc.nextInt();
            sc.nextLine();
            System.out.println("mobile number : ");
            String number = sc.nextLine();
            String querry = " insert into hotel(id, name , age , room_number ,mobile ) values ( " + id + ", '" + name + "'," + age + ","+room+", "+ number + ");";
            Statement st = cn.createStatement();
            int rows = st.executeUpdate(querry);
            System.out.println("+++++++++++++++++++++++++++");
            System.out.println(" Booked successfully !!!");
            System.out.println("+++++++++++++++++++++++++++");
            System.out.println();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    private static void checkreservation(Connection cn, Scanner sc) {
        try {
            // System.out.println(" enter id : ");
            // int id1 = sc.nextInt();
            // System.out.println("name: ");
            // String name1 = sc.nextLine();
            String querry1 = " select * from hotel ;";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(querry1);
            while (rs.next()) {
                int rss = rs.getInt("id");
                String ss = rs.getString("name");
                int age1 = rs.getInt("age");
                int room = rs.getInt("room_number");
                String number1 = rs.getString("mobile");
                System.out.println(" ID     = " + rss);
                System.out.println(" NAME   = " + ss);
                System.out.println(" AGE    = " + age1);
                System.out.println("Room no.="+room);
                System.out.println(" NUMBER = " + number1);

                System.out.println("==========================");
                System.out.println();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
            }

    private static void getroomnumber(Connection cn, Scanner sc) {
        System.out.println(" enter id : ");

        int id1 = sc.nextInt();
        sc.nextLine();
        System.out.println("name: ");
        String name1 = sc.nextLine();
        String querry1 = " select id , name , room_number from hotel where id = " + id1+" and name = '"+name1+"' ;";
        try {
            Statement st = cn.createStatement();

            ResultSet rs = st.executeQuery(querry1);
            while (rs.next()) {
                int rss = rs.getInt("id");
                String ss = rs.getString("name");
                int room = rs.getInt("room_number");

                System.out.println(" ID    = " + rss);
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println(" NAME :" + ss +" is stay in room number : "+room);
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void updateroomnumber(Connection cn, Scanner sc){
        try {
            System.out.print("Enter reservation ID to update: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            /*if (!reservationExists(cn, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

             */

            System.out.print("Enter new guest name: ");
            String newGuestName = sc.nextLine();
            System.out.print("Enter new age: ");
            int newRoomNumber = sc.nextInt();
            System.out.print("Enter new contact number: ");
            String newContactNumber = sc.next();
            String querry2= "UPDATE hotel SET name = '" + newGuestName + "', " +
                    "age  = " + newRoomNumber + ", " +
                    "mobile = '" + newContactNumber + "' " +
                    "WHERE id = " + reservationId;
            try (Statement statement = cn.createStatement()) {
                int affectedRows = statement.executeUpdate(querry2);

                if (affectedRows > 0) {
                    System.out.println("++++++++++++++++++++++++++++++++++++");
                    System.out.println("Reservation updated successfully!");
                    System.out.println("++++++++++++++++++++++++++++++++++++");
                } else {
                    System.out.println("+++++++++++++++++++++++++++++");
                    System.out.println("Reservation update failed.");
                    System.out.println("++++++++++++++++++++++++++++++");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void deletereservation(Connection cn, Scanner sc){
        System.out.println(" enter id to delete reservation ");
      int deleteId = sc.nextInt();
      String query4=" select name from hotel where id="+deleteId+";";
      String querry3 = " delete from hotel where id = "+deleteId+";";
      try(Statement st2= cn.createStatement()){
          ResultSet rs= st2.executeQuery(query4);
          while (rs.next()){
              String name = rs.getString("name");
              System.out.println("------------------------------------------------------------------");
              System.out.println(" Are you sure to delete ID :"+ deleteId+" whose name is : "+ name);
              System.out.println("------------------------------------------------------------------");
              System.out.println(" ----");
              System.out.println("| yes|");
              System.out.println("| NO |");
              System.out.println(" ----");
              sc.nextLine();
              String choice = sc.nextLine();
              if(choice.equalsIgnoreCase("yes")){
                  try(Statement st1 = cn.createStatement()) {
                      int rr= st1.executeUpdate(querry3);
                      if (rr>0){
                          System.out.println("---------------------------");
                          System.out.println(" removed successfully !!");
                          System.out.println("---------------------------");
                      }

                  } catch (SQLException e) {
                      throw new RuntimeException(e);
                  }

              }
              else {
                  System.out.println("---------------------------------------");
                  System.out.println(" try again with correct details ... ");
                  System.out.println("---------------------------------------");
              }
          }

      } catch (SQLException e) {
          throw new RuntimeException(e);
      }


    }
    private static void exit(){
        System.out.println("---------------");
        System.out.print("exiting system ");
        for (int i = 0; i <5 ; i++) {


            try {

                Thread.sleep(1000);

                System.out.print(" . ");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }



    }
