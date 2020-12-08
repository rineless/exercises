package repository;

import additional.StudentGroupSource;
import model.group.Group;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import util.finder.PathFinder;
import util.parser.ILineParser;
import util.reader.FileReader;
import util.writer.FileWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CSVGroupsRepositoryTest {
    static Path repository = PathFinder.findFromResources("data/StudentGroupData.csv");
    List<String> lines;


    @Mock
    FileReader mockReader;

    @Mock
    FileWriter mockWriter;

    @Mock
    ILineParser mockParser;


    @BeforeEach
    public void prepareMock() {
        lines = new LinkedList<>();
        lines.add("ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information");
        lines.add(StudentGroupSource.existingGroupID1);
        lines.add(StudentGroupSource.existingGroupID2);
        lines.add(StudentGroupSource.existingGroupID3);

        lenient().when(mockReader.receiveLinesAsList(repository)).thenReturn(lines);

        lenient().when(mockParser.parseLineToGroup(StudentGroupSource.existingGroupID1))
                .thenReturn(StudentGroupSource.existingGroupID1());
        lenient().when(mockParser.parseLineToGroup(StudentGroupSource.existingGroupID2))
                .thenReturn(StudentGroupSource.existingGroupID2());
        lenient().when(mockParser.parseLineToGroup(StudentGroupSource.existingGroupID3))
                .thenReturn(StudentGroupSource.existingGroupID3());

        lenient().when(mockParser.parseLineToGroup("(\\d+),(ALG|ANL|DS),(.*),(true|false),(\\d+),(.* .*),(.*)"))
                .thenThrow(new IllegalArgumentException("Group form incorrect"));
    }


    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    @DisplayName("List of all groups should be returned")
    public void getAll_ShouldReturnAllGroupsFromRepository() {
        List<Group> expected = new LinkedList<>();
        expected.add(StudentGroupSource.existingGroupID1());
        expected.add(StudentGroupSource.existingGroupID2());
        expected.add(StudentGroupSource.existingGroupID3());

        List<Group> actual = new CSVGroupsRepository(mockReader, mockWriter, mockParser).getAll();

        Assertions.assertIterableEquals(expected, actual, "Expected returned list of all groups");
    }

    @Test
    @DisplayName("Group with inputted id should be returned")
    public void getById_ShouldReturnGroup() {
        Group expected = StudentGroupSource.existingGroupID2();

        Group actual = new CSVGroupsRepository(mockReader, mockWriter, mockParser).getById(2);

        Assertions.assertEquals(expected, actual, "Expected returned group with inputted id");
    }

    @Test
    @DisplayName("Empty group should be returned if group not found")
    public void getById_WithNotExistingGroupId_ShouldReturnNull(){
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        Group actual = groupsRepository.getById(5);

        Assertions.assertNull(actual, "Expected null by not existing group id input");
    }

    @Test
    @DisplayName("Group should be added to repository")
    public void addGroup_ShouldAddGroupToRepository() {
        lenient().when(mockWriter.appendLine(StudentGroupSource.notExistingGroup, repository)).thenReturn(true);
        Mockito.when(mockParser.parseGroupToLine(StudentGroupSource.notExistingGroup()))
                .thenReturn(StudentGroupSource.notExistingGroup);
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.add(StudentGroupSource.notExistingGroup());

        Mockito.verify(mockWriter, times(1).description("Expected adding group to repository" +
                ". FileWriter appendLine method should be called with repository and line input: \\n" + StudentGroupSource.notExistingGroup))
        .appendLine("\n" + StudentGroupSource.notExistingGroup, repository);

    }

    @Test
    @DisplayName("Null should not be added to repository")
    public void addGroup_WithNullInput_ShouldChangeNothingInRepository() {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.add(null);

        Mockito.verify(mockWriter, times(0).description("Expected by null input unchanged repository. " +
                "FileWriter appendLine method should not be called with repository input. "))
                .appendLine(Mockito.anyString(), Mockito.eq(repository));
    }

    @Test
    @DisplayName("NonValid group should not be added to repository")
    public void addGroup_WithNotValidGroupInput_ShouldChangeNothingInRepository() {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.add(StudentGroupSource.notValidGroupID4());

        Mockito.verify(mockWriter, times(0).description("Expected by not valid group unchanged repository. " +
                "FileWriter appendLine method should not be called with repository input. "))
                .appendLine(Mockito.anyString(), Mockito.eq(repository));
    }

    @Test
    @DisplayName("Group information should be updated")
    public void updateGroup_ShouldUpdateGroupInformation() {
        lenient().when(mockWriter.rewriteLine(StudentGroupSource.updatedGroupID2, 2, repository)).thenReturn(true);
        Mockito.when(mockParser.parseGroupToLine(StudentGroupSource.updatedGroupID2()))
                .thenReturn(StudentGroupSource.updatedGroupID2);
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.update(StudentGroupSource.updatedGroupID2());

        Mockito.verify(mockWriter, times(1).description("Expected updating existing group in repository. " +
                "FileWriter rewriteLine should be called with repository, index and line input: \\n" + StudentGroupSource.updatedGroupID2))
                .rewriteLine(StudentGroupSource.updatedGroupID2, 2, repository);
    }

    @Test
    @DisplayName("Not existing group cannot be updated")
    public void updateGroup_WithNotExistingGroupInput_ShouldChangeNothingInRepository() {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.update(StudentGroupSource.notExistingGroup());

        Mockito.verify(mockWriter, times(0).description("Expected by not existing group input unchanged repository. " +
                "FileWriter rewriteLine method should not be called with repository input. "))
                .rewriteLine(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(repository));
    }

    @Test
    @DisplayName("Null cannot be updated")
    public void updateGroup_WithNullInput_ShouldChangeNothingInRepository() {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.update(null);

        Mockito.verify(mockWriter, times(0).description("Expected by null input unchanged repository. " +
                "FileWriter rewriteLine method should not be called with repository input. "))
                .rewriteLine(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(repository));
    }

    @Test
    @DisplayName(" By not valid group input, group cannot be updated")
    public void updateGroup_WithNotValidGroup_ShouldChangeNothingInRepository() {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.update(StudentGroupSource.notValidGroupID2());

        Mockito.verify(mockWriter, times(0).description("Expected by not valid group input unchanged repository. " +
                "FileWriter rewriteLine method should not be called with repository input. "))
                .rewriteLine(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(repository));
    }

    @Test
    @DisplayName("Group should be deleted from repository")
    public void deleteGroup_ShouldDeleteGroupFromRepository() {
        Mockito.when(mockParser.parseGroupToLine(StudentGroupSource.existingGroupID2())).thenReturn(StudentGroupSource.existingGroupID2);
        lenient().when(mockWriter.deleteLine(StudentGroupSource.existingGroupID2, repository)).thenReturn(true);
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.delete(StudentGroupSource.existingGroupID2());

        Mockito.verify(mockWriter, times(1).description("Expected adding group to repository. " +
                "FileWriter deleteLine should be called with repository and line input: " + StudentGroupSource.existingGroupID2))
                .deleteLine( StudentGroupSource.existingGroupID2, repository);
    }

    @Test
    @DisplayName("Null cannot be deleted from repository")
    public void deleteGroup_WithNullInput_ShouldChangeNothingInRepository() {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.delete(null);

        Mockito.verify(mockWriter, times(0).description("Expected by null input unchanged repository. " +
                "FileWriter deleteLine method should not be called with repository input. "))
                .deleteLine(Mockito.anyString(), Mockito.eq(repository));
    }

    @Test
    @DisplayName("By not valid group input nothing should be deleted from repository")
    public void deleteGroup_WithNotValidGroupInput_ShouldChangeNothingInRepository() throws IOException {
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.delete(StudentGroupSource.notValidGroupID2());

        Mockito.verify(mockWriter, times(0).description("Expected by not valid group input unchanged repository. " +
                "FileWriter deleteLine method should not be called with repository input. "))
                .deleteLine(Mockito.anyString(), Mockito.eq(repository));
    }

    @Test
    @DisplayName("By not existing group input nothing should be deleted from repository")
    public void deleteGroup_WithNotExistingGroupInput_ShouldChangeNothingInRepository() {
        Mockito.when(mockParser.parseGroupToLine(StudentGroupSource.notExistingGroup()))
                .thenReturn(StudentGroupSource.notExistingGroup);
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.delete(StudentGroupSource.notExistingGroup());

        Mockito.verify(mockWriter, times(1).description("Expected by not existing group input unchanged repository. " +
                "FileWriter deleteLine method should be called with repository and line input: " + StudentGroupSource.notExistingGroup))
                .deleteLine(StudentGroupSource.notExistingGroup, repository);
    }

    @Test
    @DisplayName("By group with some modified data input nothing should be deleted from repository")
    public void deleteGroup_WithGroupWithModifiedDataInput_ShouldChangeNothingInRepository() throws IOException {
        Mockito.when(mockParser.parseGroupToLine(StudentGroupSource.updatedGroupID2()))
                .thenReturn(StudentGroupSource.updatedGroupID2);
        CSVGroupsRepository groupsRepository = new CSVGroupsRepository(mockReader, mockWriter, mockParser);

        groupsRepository.delete(StudentGroupSource.updatedGroupID2());

        Mockito.verify(mockWriter, times(1).description("Expected by group with some modified data input unchanged repository. " +
                "FileWriter deleteLine method should be called with repository and line input: " + StudentGroupSource.updatedGroupID2))
                .deleteLine(StudentGroupSource.updatedGroupID2, repository);
    }
}
