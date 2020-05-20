import DBoperations.DBconnection;
import DBoperations.QueryHandler;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    private static Scanner scanner = new Scanner(System.in);

    public static void println(String string)
    {
        System.out.println(string);
    }

    public static void print(String string)
    {
        System.out.print(string);
    }

    /**
     * Prints employees data got form SELECT statement
     * @param result set of employees data selected
     */
    public static void printSet(ArrayList<ArrayList<String>> result)
    {
        for(ArrayList<String> row : result)
        {
            for(String column : row)
                print("|" + column + "\t");
            print("|\n");
        }
    }

    /**
     * Prints one particular employee's data got form SELECT statement
     * @param result set consisting of one selected employee data
     */
    public static void printEmployeeData(ArrayList<ArrayList<String>> result)
    {
        ArrayList<String> employee = result.get(0);
        println("Name: " + employee.get(1));
        println("Surname: " + employee.get(2));
        println("email: " + employee.get(3));
    }

    /**
     * Edits or adds new employee based on user's input
     * @param id  - ID of employee, -1 add a new row
     */
    public static void editEmployee(int id)
    {
        if(id != -1)
            println("skip to leave data unchanged");
        String name, surname, email;

        print("Name: ");
        name = scanner.nextLine();

        while( !RegExContainer.nameRegEx.matcher(name).matches() && (!name.isEmpty() || id == -1) )
        {
            println(name);
            println("Incorrect name");
            print("Name: ");
            name = scanner.nextLine();
        }

        print("Surname: ");
        surname = scanner.nextLine();

        while( !RegExContainer.surNameRegEx.matcher(surname).matches() && (!surname.isEmpty() || id == -1) )
        {
            println("Incorrect surname");
            print("Surname: ");
            surname = scanner.nextLine();
        }

        print("email: ");
        email = scanner.nextLine();

        while(!RegExContainer.emailRegEx.matcher(email).matches() && (!email.isEmpty() || id == -1) )
        {
            println("Incorrect email");
            print("email: ");
            email = scanner.nextLine();
        }

        if(id==-1) // add new employee
        {
            QueryHandler.execute("INSERT INTO employee(name, surname, email) VALUES " +
                    "('" + name + "', '" + surname + "', '" + email + "');");
        }
        else    // edit existing employee
        {
            if(!name.isEmpty()) QueryHandler.execute("UPDATE employee SET name = '" + name + "' WHERE id = " + id + ";");
            if(!surname.isEmpty()) QueryHandler.execute("UPDATE employee SET surname = '" + surname + "' WHERE id = " + id + ";");
            if(!email.isEmpty()) QueryHandler.execute("UPDATE employee SET email = '" + email + "' WHERE id = " + id + ";");
        }

        println("Employee data saved");
    }

    /**
     * Checks if ID of employee provided by user is valid
     * @param option option provided by user
     * @param idSet set of available employee IDs
     * @return state of ID validity
     */
    public static boolean checkOption(String option, ArrayList<ArrayList<String>> idSet)
    {
        boolean match = false;

        for(ArrayList<String> row : idSet)
            if(row.get(0).equals(option)) match = true;

        if(match)
            return true;
        else
        {
            println("Incorrect option");
            return false;
        }
    }

    public static void main(String[] args)
    {
        DBconnection.instance.getConnection();
        int employeeCount;
        boolean exit = false;
        String option;
        while(!exit)
        {
            ArrayList<ArrayList<String>> idSet = QueryHandler.select("SELECT id FROM employee");
            employeeCount = Integer.parseInt(QueryHandler.select("SELECT COUNT(id) FROM employee;").get(0).get(0));
            //printSet(idSet);
            println(
                    "1 - show all employees (" + employeeCount + ") \n" +
                    "2 - add employee \n" +
                    "3 - edit employee \n" +
                    "4 - delete employee \n" +
                    "5 - exit");

            option = scanner.nextLine();

            switch (option)
            {
                default:
                    println("Incorrect option");
                    break;

                case "1":
                    println("All employees");
                    printSet(QueryHandler.select("SELECT * FROM employee;"));
                    break;

                case "2":
                    println("Type in new employee data");
                    editEmployee(-1);
                    break;

                case "3":
                    print("Employee ID: ");
                    option = scanner.nextLine();
                    if(!checkOption(option, idSet)) break;
                    printEmployeeData(QueryHandler.select("SELECT * FROM employee WHERE id = " + option + ";"));
                    println("Type in new data");
                    editEmployee(Integer.parseInt(option));
                    break;

                case "4":
                    print("Employee ID: ");
                    option = scanner.nextLine();
                    if(!checkOption(option, idSet)) break;
                    printEmployeeData(QueryHandler.select("SELECT * FROM employee WHERE id = " + option + ";"));

                    println("Delete employee? (1 - Yes, 0 - No)");
                    String deleteOption = scanner.nextLine();
                    if(deleteOption.equals("1"))
                    {
                        QueryHandler.execute("DELETE FROM employee WHERE id = " + option + ";");
                        println("employee deleted");
                    }
                    else
                        println("Operation canceled");
                    break;

                case "5":
                    println("Exiting");
                    exit = true;
                    break;
            }
        }
    }
}
