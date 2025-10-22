---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TutorPal User Guide

TutorPal aims to help small math tuition centre owners manage students effortlessly by centralizing student contact info, grades, attendance, payment status, subject assignments, tutors, and class schedules in one easy-to-use command-line system. TutorPal aims to help tution center owners save time, make less errors, and focus on teaching instead of paperwork.

TutorPal is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TutorPal can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F11-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

TODO

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [a/ADDRESS]` can be used as `n/John Doe a/Kent Ridge` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[c/MORE_CLASSES]…​` can be used as ` ` (i.e. 0 times), `c/s4mon1600`, `c/s4mon1600 c/s4mon1400` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `exit 123`, it will be interpreted as `exit`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the [help page](https://ay2526s1-cs2103t-f11-2.github.io/tp/UserGuide.html), as well as a quick summary of all commands and how to use them.

![help message](images/helpMessage.png) #TODO

Format: `help`


### Adding a person: `add`
Adds a student or tutor to the system.

Format:
add r/ROLE n/NAME p/PHONE e/EMAIL c/CLASS [c/MORE_CLASSES]... [a/ADDRESS]

Examples:
add r/student n/Kevin p/98761234 e/kevin@gmail.com a/Kent Ridge c/s4mon1600
add r/tutor n/Calvin p/99998888 e/calvin@gmail.com a/Jurong West c/s4mon1600 c/s1mon1800

Notes:

Details:
* ROLE must be either student or tutor.
* At least one class (c/CLASS) is required.
* CLASS format: First 2 characters must be s1, s2, s3, s4, or s5 (class level), followed by 3 lowercase letters for the day (mon, tue, wed, thu, fri, sat, sun), and ending with 4 digits representing time in 24-hour format (e.g., s4mon1600 for Secondary 4 Monday 4:00 PM).
* Address (a/ADDRESS) is optional.

<box type="tip" seamless>

**Tip:** A student can only have 1 class. A tutor can have one or more classes.
</box>

### Listing all persons : `list`

Shows a list of all persons in the address book.

Formats:
* `list`
* `list c/CLASS`
* `list tu/TUTOR`

Details:
* `list` shows all persons' contact
* `list c/...` shows students whose class matches the given code or prefix
    * Accepts same class format as add: s[1-5][day][time] (e.g. s4mon1600)
    * Prefix matching is allowed:
        * s4 - all Secondary 4 classes (any day/time)
        * s4mon - all Secondary 4 Monday classes (any time)
    * If you provide only part of the class, it acts as a wildcard for the remaining parts
* `list tu/...` shows students enrolled in any class taught by tutors whose name contains the given substring
    * Name matching uses Java's `String.contains` behaviour
    * If multiple tutors match, students from all those tutors' classes are listed (duplicates removed)
* Only one filter may be used per command (use either `c/...` or `tu/...`)

Examples:
* `list` - shows all persons
* `list c/s4` - shows **all Sec 4 students** across day/time
* `list c/s4mon1600` - shows **Sec 4 Monday 1600** students only
* `list tu/Alex` - **students** taught by any tutor whose name contains `Alex`
* `list tu/` - shows **all students** assigned to at least one tutor

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [r/ROLE][n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [c/CLASS]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing classes, the existing classes of the person will be removed i.e adding of classes is not cumulative.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.

### Locating students and tutors by name: `find`

Finds students and tutors whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `chong` will match `Chong`
* The order of the keywords does not matter. e.g. `Chong Rui` will match `Rui Chong`
* Only the name is searched.
* Only full words will be matched e.g. `Shen` will not match `Sheng`
* Persons matching at least one keyword will be returned (i.e. `OR` search).

Examples:
* `find Sheng` returns `Sheng` and `Yong Sheng`
* `find Lee Sen More` returns `Lee Ze Xuan`, `Sen Yong Sheng` and `More Robin`
  
### Marking attendance for students : `mark`

Marks the specified student as having attended the class for that week.

Format: `mark INDEX w/WEEK`

* Marks the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1,2,3,...
* Weeks are grouped by month, and identified with a number 1 - 4 representing 1st to 4th week.
* e.g. `w/W2-10-2025` represents the second week in Oct 2025.
* Using an invalid week number or invalid month will result in an error displayed.
* Tutors cannot be marked. Attempting to do so will result in an error displayed.

Examples:
* `mark 3 w/W2-10-2025` marks the 3rd person in the displayed list as having attended the second week in Oct 2025.

![markimage](images/mark.png)

### Unmarking attendance for students : `unmark`

Unmarks the specified student as having attended the class for that week.

Format: `unmark INDEX w/WEEK`

* Unmarks the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1,2,3,...
* Weeks are grouped by month, and identified with a number 1 - 4 representing 1st to 4th week.
* e.g. `w/W2-10-2025` represents the second week in Oct 2025.
* Using an invalid week number or invalid month will result in an error displayed.
* Tutors cannot be unmarked. Attempting to do so will result in an error displayed.

Examples:
* `unmark 3 w/W2-10-2025` unmarks the 3rd person in the displayed list as having attended the second week in Oct 2025.

### Managing payments : `pay`

Records monthly fee payments and show each person's payment status

Format: `pay INDEX m/MM-YYYY [m/MM-YYYY]`

Details:
* Marks the specified month and year as paid for the person at `INDEX`
* Each person has a **Join Month** in MMMM-YYYY. Billing starts from this month inclusive
* Month format must be MM-YYYY (e.g., 04-2025)
* By default, paying for months **after the current month** and **before Join Month** are not allowed
* Paid - every month from **Join Month** up to **and including** the current month is paid
* Unpaid - all months **before** the current month are paid, but the **current month** is not yet paid
* Overdue - there exists **any unpaid month** before the current month 

Examples (assume today is Oct 2025):
* `pay 3 m/09-2025` - marks Sept 2025 as paid for person #3

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add r/ROLE n/NAME p/PHONE e/EMAIL c/CLASS [c/MORE_CLASSES]... [a/ADDRESS]​` <br> e.g., `add r/student n/Kevin p/98761234 e/kevin@gmail.com a/Kent Ridge c/s4mon1600`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [r/ROLE][n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [c/CLASS]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Exit**   | `exit`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list [c/CLASS] [tu/TUTOR]`
**Mark**   | `mark INDEX w/WEEK`
**UnMark** | `unmark INDEX w/WEEK`
**Help**   | `help`
