package com.softuni.StudentClubs.unitTests;

import com.softuni.StudentClubs.dto.ClubDto;
import com.softuni.StudentClubs.models.entities.Club;
import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.repository.ClubRepository;
import com.softuni.StudentClubs.repository.UserRepository;
import com.softuni.StudentClubs.security.SecurityUtil;
import com.softuni.StudentClubs.service.impl.ClubServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ClubServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private ClubServiceImpl clubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllClubs() {
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club();
        Club club2 = new Club();

        club1.setId(1L);
        club2.setId(2L);

        clubs.add(club1);
        clubs.add(club2);

        when(clubRepository.findAll()).thenReturn(clubs);

        List<ClubDto> result = clubService.findAllClubs();

        assertEquals(2, result.size());

    }

    @Test
    void testFindClubById() {
        Club club = new Club();
        club.setId(1L);

        when(clubRepository.findById(anyLong())).thenReturn(java.util.Optional.of(club));

        ClubDto result = clubService.findClubById(1L);

        assertEquals(1L, result.getId().longValue());
    }

    @Test
    void testSaveClub() {
        // Mock data
        ClubDto clubDto = new ClubDto();
        clubDto.setTitle("Test Club");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        // Mock security context
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        // Mock behavior
        when(userRepository.findByUsername(anyString())).thenReturn(userEntity);
        when(clubRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        Club result = clubService.saveClub(clubDto);

        // Verify
        assertEquals(userEntity, result.getCreatedBy());
        verify(clubRepository, times(1)).save(any());

        // Reset security context
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDeleteClubById() {
        clubService.deleteClubById(1L);

        verify(clubRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchByTitle() {
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club();
        Club club2 = new Club();

        club1.setId(1L);
        club2.setId(2L);

        clubs.add(club1);
        clubs.add(club2);

        when(clubRepository.searchByTitle(anyString())).thenReturn(clubs);

        List<ClubDto> result = clubService.searchByTitle("Test");

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateClub() {
        // Mock data
        ClubDto clubDto = new ClubDto();
        clubDto.setTitle("Test Club");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        // Mock security context
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        // Mock behavior
        when(userRepository.findByUsername(anyString())).thenReturn(userEntity);
        when(clubRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        clubService.updateClub(clubDto);

        // Verify
        verify(clubRepository, times(1)).save(any());

        // Reset security context
        SecurityContextHolder.clearContext();
    }

    @Test
    void findClubsByUserId(){
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club();
        Club club2 = new Club();

        club1.setId(1L);
        club2.setId(2L);

        clubs.add(club1);
        clubs.add(club2);

        when(clubRepository.findClubsByCreatedBy_Id(anyLong())).thenReturn(clubs);

        List<ClubDto> result = clubService.findClubsByUserId(1L);

        assertEquals(2, result.size());
    }
}
