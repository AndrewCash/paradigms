import java.lang.arraylist;

class Student
{
    String name;
    int id;
    int points;
}

class Roster
{
    ArrayList<Student> students;    
    String name;

    Roster(String n)
    {
        name = n;
    }

    void add(Student s)
    {
        students.add(s);
    }

    int size()
    {
        students.size();
    }

    Student getStudent(int _id)
    {
        for (int i=0; i<students.size(); i++)
        {
            if (students[i].id == _id)
                return students[i];
        }
    }

    int getPoints(int _id)
    {
        try
        {
            for (int i=0; i<students.size(); i++)
            {
                if (students[i].id == _id)
                    return students[i];
            }
        } catch (Exception e)
        {
            System.out.println("Error");
            System.exit(1); }
    }
}
