package util.validation;

import model.group.Group;

import java.util.Objects;

public class GroupValidation implements IValidation {

    @Override
    public boolean isValid(Object group) {
        if (Objects.nonNull(group)) {
            if (group instanceof Group)
                return isValid((Group) group);
        }
        return false;
    }

    public static boolean isValid(Group group) {
        if (Objects.nonNull(group)) {
            if (Objects.nonNull(group.getGroupName()) & Objects.nonNull(group.getLanguage())
                    & Objects.nonNull(group.getResponsibleForGroup()) & Objects.nonNull(group.getContactInformation()))
                return true;
        }
        return false;
    }
}
