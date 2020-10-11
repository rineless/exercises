package repository;

import model.group.Group;
import model.group.GroupNames;
import org.junit.jupiter.api.*;
import util.finder.PathFinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CSVGroupsRepositoryTest {
    static Path repository = PathFinder.findFromResources("data/StudentGroupData.csv");

    @BeforeEach
    public void fillTestRepository() throws IOException {
        List<String> lines = new LinkedList<>();
        lines.add("ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information");
        lines.add("1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de");
        lines.add("2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de");
        lines.add("3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de");
        Files.write(repository, lines);
    }

    @AfterEach
    public void clearTestRepository() throws IOException {
        Files.write(repository, "".getBytes());
    }

    @Test
    @DisplayName("List of all groups should be returned")
    public void getAll_ShouldReturnAllGroupsFromRepository() {
        List<Group> expected = new LinkedList<>();
        expected.add(new Group().setId(1).setGroupName(GroupNames.DS).setLanguage(new Locale("germany")).isOnlineAccessible(true).setMaxAttendeesPresent(30)
                .setResponsibleForGroup(new String[]{"Adam","Becker"}).setContactInformation("adam.becker@myuni.de"));
        expected.add(new Group().setId(2).setGroupName(GroupNames.ALG).setLanguage(new Locale("english")).isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de"));
        expected.add(new Group().setId(3).setGroupName(GroupNames.ANL).setLanguage(new Locale("english")).setMaxAttendeesPresent(15)
                .setResponsibleForGroup(new String[]{"Karol","Maier"}).setContactInformation("karol.maier@myuni.de"));

        List<Group> actual = new CSVGroupsRepository().getAll();

        Assertions.assertIterableEquals(expected, actual, "Expected returned list of all groups");
    }

    @Test
    @DisplayName("Group with inputted id should be returned")
    public void getById_ShouldReturnGroup() {
        Group expected = new Group().setId(2).setGroupName(GroupNames.ALG).setLanguage(new Locale("english")).isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de");

        Group actual = new CSVGroupsRepository().getById(2);

        Assertions.assertEquals(expected, actual, "Expected returned group with inputted id");
    }

    @Test
    @DisplayName("Empty group should be returned if group not found")
    public void getById_WithNotExistingGroupId_ShouldReturnNull(){
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();

        Group actual = groupsRepository.getById(5);

        Assertions.assertNull(actual, "Expected null by not existing group id input");
    }

    @Test
    @DisplayName("Group should be added to repository")
    public void addGroup_ShouldAddGroupToRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n" +
                "4,ALG,germany,true,20,Jas Merol,jas.marol@myuni.de\n";

        groupsRepository.add(new Group().setId(4).setGroupName(GroupNames.ALG).setLanguage(new Locale("germany")).isOnlineAccessible(true)
                .setMaxAttendeesPresent(20).setResponsibleForGroup(new String[]{"Jas","Merol"}).setContactInformation("jas.marol@myuni.de"));
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected adding group to repository");
    }

    @Test
    @DisplayName("Null should not be added to repository")
    public void addGroup_WithNullInput_ShouldChangeNothingInRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.add(null);
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by null input unchanged repository");
    }

    @Test
    @DisplayName("NonValid group should not be added to repository")
    public void addGroup_WithNotValidGroupInput_ShouldChangeNothingInRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.add(new Group().setId(4).setLanguage(new Locale("germany")).isOnlineAccessible(true)
                .setResponsibleForGroup(new String[]{"Jas","Merol"}));
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by not valid group unchanged repository");
    }

    @Test
    @DisplayName("Group information should be updated")
    public void updateGroup_ShouldUpdateGroupInformation() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,false,15,Mai Theon,mai.theon@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.update(new Group().setId(2).setGroupName(GroupNames.ALG).setLanguage(new Locale("english")).isOnlineAccessible(false)
                .setMaxAttendeesPresent(15).setResponsibleForGroup(new String[]{"Mai","Theon"}).setContactInformation("mai.theon@myuni.de"));
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected updating existing group in repository");
    }

    @Test
    @DisplayName("Not existing group cannot be updated")
    public void updateGroup_WithNotExistingGroupInput_ShouldChangeNothingInRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.update(new Group().setId(10).setGroupName(GroupNames.ALG).setLanguage(new Locale("german")).isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de"));
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by not existing group input unchanged repository");
    }

    @Test
    @DisplayName("Null cannot be updated")
    public void updateGroup_WithNullInput_ShouldChangeNothingInRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.update(null);
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by null input unchanged repository");
    }

    @Test
    @DisplayName(" By not valid group input, group cannot be updated")
    public void updateGroup_WithNotValidGroup_ShouldChangeNothingInRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.update(new Group().setId(2).setLanguage(new Locale("english")).isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de"));
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by not valid group input unchanged repository");
    }

    @Test
    @DisplayName("Group should be deleted from repository")
    public void deleteGroup_ShouldDeleteGroupFromRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.delete(new Group().setId(2).setGroupName(GroupNames.ALG).setLanguage(new Locale("english")).isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de"));
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected adding group to repository");
    }

    @Test
    @DisplayName("Null cannot be deleted from repository")
    public void deleteGroup_WithNullInput_ShouldChangeNothingInRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.delete(null);
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by null input unchanged repository");
    }

    @Test
    @DisplayName("By not valid group input nothing should be deleted from repository")
    public void deleteGroup_WithNotValidGroupInput_ShouldChangeNothingInRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.delete(new Group().setId(2).setLanguage(new Locale("english")).isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de"));
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by not valid group input unchanged repository");
    }

    @Test
    @DisplayName("By not existing group input nothing should be deleted from repository")
    public void deleteGroup_WithNotExistingGroupInput_ShouldChangeNothingInRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.delete(new Group().setId(18).setGroupName(GroupNames.ALG).setLanguage(new Locale("english")).isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shery","Brick"}).setContactInformation("shery.brick@myuni.de"));
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by not existing group input unchanged repository");
    }

    @Test
    @DisplayName("By group with some modified data input nothing should be deleted from repository")
    public void deleteGroup_WithGroupWithModifiedDataInput_ShouldChangeNothingInRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository();
        String expected = "ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information\n" +
                "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de\n" +
                "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de\n" +
                "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de\n";

        groupsRepository.delete(new Group().setId(2).setGroupName(GroupNames.DS).setLanguage(new Locale("english")).isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de"));
        String actual = Files.lines(repository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by group with some modified data input unchanged repository");
    }
}
