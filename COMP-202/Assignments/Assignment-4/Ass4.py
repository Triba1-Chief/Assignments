#Samuel Kinyua
# Student ID removed
#3rd April 2023
#This program will build an application that helps represent the organizational
#chart and the relationships of its different job titles at McGill.

#CONSTANTS
P_SUPERVISOR_ID = -1 #Principal supervisor id is -1
PRINCIPAL_JOB_ID = 4000


# Question 1
class University:
    """
        A data type that represents a university.
        
        Instance attributes:
            name (string): contains the name of the university
            location (string): contains the location of the university
            motto (string): contains the motto of the university
            date (tuple<int>): contains the date of creation of the university
    """
    


    def __init__(self, name = "University", location = "Somewhere",
                 motto = "Something", day = 1, month = 1, year = 2023):
        """
            (string, string, string, integer, integer, integer) -> (University)
            
            Returns a new object with the type Univesity.
            
            >>> ets = University('Ecole de technologie Superieure',
            'Montreal,Canada','Engineering for industry',31,1,1974)
            >>> ets.uni_location
            'Montreal,Canada'
            
            >>> strath = University('Strathmore University','Nairobi,Kenya',
            'Ut onmes unum sint',12,5,1980)
            >>> strath.uni_motto
            'Ut onmes unum sint'
            
            >>> nibs = University("NIBS", "Wanderland,Disney",
                                  "Come one come all", 20,0,1990)
            Traceback (most recent call last):
              File "<stdin>", line 1, in <module>
                raise ValueError("All the values for day, month and year should"
            ValueError: All the values for day, month and year should be positive
        """
        
        if day < 1 or month < 1 or year < 1: #checks if each value in date is
                                            #positive and greater than zero
            raise ValueError("All the values for day, month and year should"
                                 + " be positive")
            
        self.name = name
        self.location = location
        self.motto = motto
        self.date = (day, month, year)
    
    
    
    def __str__(self):
        """
            (University) -> (string)
            
            Returns a string for the object of class University.
            
            >>> strath = University('Strathmore University','Nairobi,Kenya',
            'Ut onmes unum sint',12,5,1980)
            >>> print(strath)
            University of Strathmore University was founded in (12, 5, 1980)
            Its main campus is located in Nairobi,Kenya
            Its Motto is: Ut onmes unum sint
            
            >>> print(nibs)
            Traceback (most recent call last):
            File "<stdin>", line 1, in <module>
            NameError: name 'nibs' is not defined
            
            >>> ets = University('Ecole de technologie Superieure',
            'Montreal,Canada','Engineering for industry',31,1,1974)
            >>> print(ets)
            University of Ecole de technologie Superieure was founded in
            (31, 1, 1974)
            Its main campus is located in Montreal,Canada
            Its Motto is: Engineering for industry
            
        """
        
        return ("University of " + self.name + " was founded in " + 
                str(self.date) + "\n" + "Its main campus is located in " +
                self.location + "\n" + "Its Motto is: " + self.motto)
    


#Question 2
class JobPosition:
    """
        A data type that represents a job.
        
        Class attributes:
            id_description_dict(dictionary): stores job descriptions as
            dictionary keys and their corresponding values associated with a job

        Instance attributes:
            job_id(integer): stores the identification number of a job position
            description(string): describes the position
    """
    
    
    
    id_description_dict = copy.deepcopy({"Teaching Assistant":[0,99],
                                         "Professor":[100,499],
                                         "Faculty Lecturer":[500,999],
                                         "Department Chair":[1000,1999],
                                         "Dean":[2000,2999],
                                         "Vice Principal":[3000,3999],
                                         "Principal":[4000]})
    
    
    
    def id_to_description(self, job_id):
        """
            (integer) -> (string)
            
            Returns the job description based on job id.
            
            >>> s = JobPosition(99)
            >>> print(s)
            'Teaching Assistant'
            
            >>> s = JobPosition(1000)
            >>> print(s)
            'Department Chair'
            
            >>> s = JobPosition(4000)
            >>> print(s)
            'Principal'
        """
        
        for key in JobPosition.id_description_dict:
            l_limit = JobPosition.id_description_dict[key][0]
                
            if job_id == PRINCIPAL_JOB_ID and l_limit == PRINCIPAL_JOB_ID:
                return key #Returns Principal in this case
            else: #Compares if job_id between the key limits
                u_limit = JobPosition.id_description_dict[key][1]  
            
                if job_id >= l_limit and job_id <= u_limit:
                    return key #Job description
                
    
    
    def __init__(self, id_no):
        """
            (integer) -> (JobPosition)
            
            Returns an object with the type JobPosition with its job id and
            description.
            
            >>> p = JobPosition(350)
            >>> p.job_id
            350
            
            >>> p = JobPosition()
            Traceback (most recent call last):
              File "<stdin>", line 1, in <module>
            TypeError: JobPosition.__init__() missing 1 required positional
            argument: 'id_no'
            
            >>> p = JobPosition(9000)
            >>> p.description
            'Principal'
        """
        
        self.job_id = id_no
        self.description = JobPosition.id_to_description(self, self.job_id)
        
    
    
    def __str__(self):
        """
            () -> (string)
            
            Returns a string for the object of class JobPosition.
            
            >>> s = JobPosition(99)
            >>> print(s)
            'Teaching Assistant'
            
            >>> s = JobPosition(1000)
            >>> print(s)
            'Department Chair'
            
            >>> s = JobPosition(4000)
            >>> print(s)
            'Principal'
        """
            
        return self.description
    
    
    
    def get_id(self):
        """
            () -> (integer)
            
            Returns the value of the object's job_id.
            
            >>> s = JobPosition(600)
            >>> s.get_id()
            600
            
            >>> s = JobPosition(490)
            >>> s.get_id()
            490
            
            >>> s = JobPosition(99)
            >>> q.get_id()
            Traceback (most recent call last):
              File "<stdin>", line 1, in <module>
            NameError: name 'q' is not defined
              
        """
        
        return self.job_id
    
    
²È="25niversity = University(name, location, motto, day, month, year)
             
            #Generating employee objects and employee_list
            for obj in fobj[7:]:
                if not obj == "" and not obj == " ": #Getting values as
                    #first name, last name, reference, job_id, sup_ref
                    new_obj = obj.split(",") #makes a list of employees
                    name = new_obj[2].split()
                    f_name = name[0]
                    l_name = name[1]
                    ref = int(new_obj[0])
                    job_id = int(new_obj[1])
                    sup_ref = int(new_obj[3])
                    employee_list.append(Employee(f_name, l_name, ref, job_id,
                                                sup_ref))
               
        except FileNotFoundError:
            employee_list = []
            
            self.university = University() #Default values of University used
            employee_list.append(Employee()) #Default values of Employee used
            print("File does not exist")
                        
        except:
            employee_list = []
            
            self.university = University() #Default values of University used
            employee_list.append(Employee()) #Default values of Employee used
            print("There was an error with the file")
            
        finally:
            file_content.close()
            return employee_list
        
        
    
    def __init__(self, filename):
        """
            (string) -> (OrganizationalChart)
            
            Returns a list of employee objects.
            
            >>> a = OrganizationalChart("Mcgill_OrgChar.py")
            File does not exist
            >>> a = OrganizationalChart("Mcgill_OrgChart.py")
            There was an error with file
            >>> a = OrganizationalChart()
            Traceback (most recent call last):
              File "<stdin>", line 1, in <module>
            TypeError: OrganizationalChart.__init__() missing 1 required
            positional argument: 'filename'
        """
           
        self.employee_list = OrganizationalChart.load_chart(self, filename)
        
    
    
    def __str__(self):
        """
            () -> (string)
            
            >>> a = OrganizationalChart("Mcgill_OrgChart.py")
            >>> print(a)

            H.Deep Saini - Principal
            Angela Campbell - Vice Principal
            Bruce Lennox - Dean
            Gregor Fussmann - Department Chair
            Mathieu Blanchette - Department Chair
            Oana Balmau - Professor
            Jin Guo - Professor
            Christophe Dubach - Professor
            Guilia Albertini - Faculty Lecturer
            Faten Mhiri - Faculty Lecturer
            Joseph Vybihall - Faculty Lecturer
            Jacob Errington - Faculty Lecturer
            Saad Yousaf - Teaching Assistant
            MirHamed JafarzadehAsl - Teaching Assistant
            Neil Rahman - Teaching Assistant
        """
        
        employee_string = ""
        
        for employee in self.employee_list:
            employee_string += ("\n" + str(employee))
        return employee_string
            
    
    
    def is_in_list(self, employee):
        """
            (Employee) -> (boolean)
            
            Returns True if an Employee object exists in the employee_list
            otherwise it will return False.
            
            >>> a = OrganizationalChart("Mcgill_OrgChart.py")
            >>> e = Employee('Great', 'Muddah', 10, 10, 10)
            >>> a.is_in_list(e)
            False
            
            >>> a = OrganizationalChart("Mcgill_OrgChart.py")
            >>> e = Employee('Great', 'Muddah', 10, 10, 10)
            >>> a.is_in_list(e)
            True
            
            >>> a = OrganizationalChart("Mcgill_OrgChart.py")
            >>> e = Employee('Great', 'Muddah', 10, 10, 10)
            >>> a.is_in_list()
            Traceback (most recent call last):
              File "<stdin>", line 3, in <module>
            TypeError: OrganizationalChart.is_in_list() missing 1 required
            positional argument: 'employee'
        """
        
        return employee in self.employee_list


    
    def find_hierarchical_line(self, employee):
        """
            (employee) -> (List<string>)
            
            Returns a list of employee's object where the input employee is the
            last item in the list, preceded by their direct supervisor, preceded
            by the one above until the principal supervisor as the first item
            in the list.
            
            >>> a = OrganizationalChart("Mcgill_OrgChart.py")
            >>> e1 = Employee('Neil','Rahman',15,20,10)
            >>> d = a.find_hierarchichal_line(e1)
            >>> print(d)
            ['H.Deep Saini - Principal', 'Angela Campbell - Vice Principal',
            'Bruce Lennox - Dean', 'Mathieu Blanchette - Department Chair',
            'Faten Mhiri - Faculty Lecturer', 'Neil Rahman - Teaching Assistant']   
        """
        
        hierarchy_list = []
        
        if not self.is_in_list(employee):#Checks if employee is in employee_list
            return hierarchy_list
        else:
            hierarchy_list.append(employee)
            
            Employee.nb_employee -= 1 #Removes replicate employee when finding
                                      #hierarchical line
            
            while not employee.supervisor == P_SUPERVISOR_ID: #Repeats as long
                #as supervisor isn't the principal
                
                #Generate the current supervisor as the new employee who in
                #turn look for their supervisor.
                for new_emp in self.employee_list:
                    if employee.supervisor == new_emp.ref and (not employee.ref
                                                               == new_emp.ref):
                        #ensures employee and supervisor are two different people.
                        employee = new_emp # Supervisor becomes new employee
                        hierarchy_list.append(employee) #Adds supervisor to the
                                                        #hierachy_list
        
        return hierarchy_list[::-1] #reversing the hierarchical order
    
    
                
    def print_hierarchical_line(self, employee):
        """
            (employee) -> (string)
            
            Prints the hierarchical line of supervisors starting from the
            principal supervisor to the input employee object.
            
            >>> a = OrganizationalChart("Mcgill_OrgChart.py")
            >>> e1 = Employee('Neil','Rahman',15,20,10)
            >>> a.print_hierarchical_line(e1)
            +-> H.Deep Saini - Principal
            | +-> Angela Campbell - Vice Principal
            | | +-> Bruce Lennox - Dean
            | | | +-> Mathieu Blanchette - Department Chair
            | | | | +-> Faten Mhiri - Faculty Lecturer
            | | | | | +-> Neil Rahman - Teaching Assistant
        """
        
        counter = 0
        
        for name in self.find_hierarchical_line(employee):
            print("| " * counter + "+" + "-" + ">" + " " + str(name))
            #Multiplies | by the value of counter
            counter += 1

a = OrganizationalChart("Mcgill_OrgChart.py")
e1 = Employee('Faten','Mhiri',10,570,5)
a.print_hierarchical_line(e1)