package com.synechisveltiosi.apis.app365.campaign.service;

import com.synechisveltiosi.apis.app365.address.helper.AddressHelper;
import com.synechisveltiosi.apis.app365.campaign.TeamMemberNotFoundException;
import com.synechisveltiosi.apis.app365.campaign.entity.ExtendedTeamMember;
import com.synechisveltiosi.apis.app365.campaign.entity.TeamLevel;
import com.synechisveltiosi.apis.app365.campaign.entity.TeamMember;
import com.synechisveltiosi.apis.app365.campaign.repository.ExtendedTeamMemberRepository;
import com.synechisveltiosi.apis.app365.campaign.repository.TeamMemberRepository;
import com.synechisveltiosi.apis.app365.citizens.entity.Citizen;
import com.synechisveltiosi.apis.app365.citizens.entity.CitizenAddress;
import com.synechisveltiosi.apis.app365.citizens.exception.CitizenNotFoundException;
import com.synechisveltiosi.apis.app365.citizens.service.CitizenAddressService;
import com.synechisveltiosi.apis.app365.citizens.service.CitizenService;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final ExtendedTeamMemberRepository extendedTeamMemberRepository;
    private final CitizenService citizenService;
    private final UserService userService;
    private final CitizenAddressService citizenAddressService;

    @Autowired
    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository,
                                 ExtendedTeamMemberRepository extendedTeamMemberRepository,
                                 CitizenService citizenService, UserService userService,
                                 CitizenAddressService citizenAddressService) {

        this.teamMemberRepository = teamMemberRepository;
        this.extendedTeamMemberRepository = extendedTeamMemberRepository;
        this.citizenService = citizenService;
        this.userService = userService;
        this.citizenAddressService = citizenAddressService;
    }

    @Override
    public Optional<TeamMember> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Team member id should not be null or 0");

        return teamMemberRepository.findById(id);
    }

    @Override
    public Optional<TeamMember> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Team member id should not be null or blank");

        return teamMemberRepository.findByMemberId(id);
    }

    @Override
    public Optional<TeamMember> findByIdCard(String idCard) {
        if (StringUtils.isBlank(idCard))
            throw new BadRequestException("Team member id card should not be null or blank");

        return teamMemberRepository.findByCitizenId_IdCard(idCard);
    }

    @Override
    public Optional<ExtendedTeamMember> findTeamMemberByUserIdAndIdCard(Long userId, String idCard) {
        if (userId == null || userId == 0) throw new BadRequestException("Parent user id should not be null or 0");

        if (StringUtils.isBlank(idCard))
            throw new BadRequestException("Team member id card should not be null or blank");

        return extendedTeamMemberRepository.findByUserId_IdAndCitizenId_IdCard(userId, idCard);
    }

    @Override
    public Page<TeamMember> findAll(Long userId, String idCard, Pageable pageable) {
        if (userId == null || userId == 0) throw new BadRequestException("Parent user id should not be null or 0");

        if (StringUtils.isBlank(idCard))
            throw new BadRequestException("Team member id card should not be null or blank");

        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return teamMemberRepository.findAllByUserId_IdAndCitizenId_IdCardNot(userId, idCard, pageable);
    }

    @Override
    public Page<ExtendedTeamMember> findAllTeamMembers(Long userId, String idCard, Pageable pageable) {
        if (userId == null || userId == 0) throw new BadRequestException("Parent user id should not be null or 0");

        if (StringUtils.isBlank(idCard))
            throw new BadRequestException("Team member id card should not be null or blank");

        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return extendedTeamMemberRepository.findAllByUserId_IdAndCitizenId_IdCardNot(userId, idCard, pageable);
    }

    @Override
    public List<TeamLevel> findLevelMembers(String idCard) {
        Optional<TeamMember> teamMemberOptional = findByIdCard(idCard);
        if (!teamMemberOptional.isPresent()) throw new TeamMemberNotFoundException();

        return teamMemberRepository.getTeamLevelMembers(teamMemberOptional.get().getId());
    }

    @Transactional
    @Override
    public TeamMember save(Long userId, String idCard) {
        // Validate parent user id
        if (userId == null || userId == 0) throw new BadRequestException("Parent user id should not be null or 0");

        // Validate potential member id card
        if (StringUtils.isBlank(idCard))
            throw new BadRequestException("Team member id card should not be null or blank");

        // Find the parent
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // If parent user does not have register his id card, force him to do so
        if (StringUtils.isBlank(userOptional.get().getIdCard()))
            throw new BadRequestException("You should add an id card first to be able to add user to your team.");

        // Find the citizen to add as team member
        Optional<Citizen> citizenOptional = citizenService.findByIdCard(idCard);
        if (!citizenOptional.isPresent()) throw new CitizenNotFoundException();

        // Find the parent citizen
        Optional<Citizen> parentCitizenOptional = citizenService.findByIdCard(userOptional.get().getIdCard());
        if (!parentCitizenOptional.isPresent()) throw new CitizenNotFoundException();

        // Make sure the user is not trying to add himself as member
        if (Objects.equals(parentCitizenOptional.get().getIdCard(), citizenOptional.get().getIdCard()))
            throw new BadRequestException("You cannot add yourself as member.");

        // Silently add the parent user as a team member if he is not already added.
        // This is necessary to be able to stop recursion query in other part later
        Optional<TeamMember> teamMemberOptional = findByIdCard(userOptional.get().getIdCard());
        TeamMember parentTeamMember = null;
        if (!teamMemberOptional.isPresent()) {
            // Create the parent user
            TeamMember teamMember = new TeamMember();
            teamMember.setCitizenId(parentCitizenOptional.get());
            teamMember.setUserId(userOptional.get());
            teamMember.setParentId(null);

            // Save the parent user
            parentTeamMember = teamMemberRepository.save(teamMember);
        }

        // If parent member is null, that's mean our parent number was already there
        if (parentTeamMember == null) parentTeamMember = teamMemberOptional.get();

        // Create the new team member
        TeamMember newTeamMember = new TeamMember();
        newTeamMember.setCitizenId(citizenOptional.get());
        newTeamMember.setUserId(userOptional.get());
        newTeamMember.setParentId(parentTeamMember.getId());

        try {
            return teamMemberRepository.save(newTeamMember);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("This citizen might be already someone else member.");
        }
    }

    @Transactional
    @Override
    public TeamMember patchAddress(String memberId, Map<String, Object> addressPatch) {
        // Find the team member
        TeamMember teamMember = findById(memberId).orElseThrow(TeamMemberNotFoundException::new);

        // Save the user address
        CitizenAddress citizenAddress = AddressHelper.toCitizenAddress(addressPatch);
        citizenAddress.setCitizenId(teamMember.getCitizenId());
        citizenAddressService.patch(citizenAddress);

        return findById(teamMember.getId()).orElse(teamMember);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Team member id should not be null or 0");

        Optional<TeamMember> teamMemberOptional = findById(id);
        if (!teamMemberOptional.isPresent()) throw new TeamMemberNotFoundException();

        teamMemberRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByIdCard(String idCard) {
        if (StringUtils.isBlank(idCard))
            throw new BadRequestException("Team member id card should not be null or blank");

        Optional<TeamMember> teamMemberOptional = findByIdCard(idCard);
        if (!teamMemberOptional.isPresent()) throw new TeamMemberNotFoundException();

        teamMemberRepository.deleteByCitizenId_IdCard(idCard);
    }
}
