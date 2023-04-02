package ug.co.absa.paybill.service.mapper;

import org.mapstruct.*;
import ug.co.absa.paybill.domain.ContactInfo;
import ug.co.absa.paybill.domain.Student;
import ug.co.absa.paybill.service.dto.ContactInfoDTO;
import ug.co.absa.paybill.service.dto.StudentDTO;

/**
 * Mapper for the entity {@link ContactInfo} and its DTO {@link ContactInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactInfoMapper extends EntityMapper<ContactInfoDTO, ContactInfo> {
    @Mapping(target = "student", source = "student", qualifiedByName = "studentId")
    ContactInfoDTO toDto(ContactInfo s);

    @Named("studentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StudentDTO toDtoStudentId(Student student);
}
