package com.task.lot.service;

import com.task.lot.model.response.FullStatsResponse;
import com.task.lot.model.UserVisitDTO;
import com.task.lot.model.response.UserVisitStatsResponse;
import com.task.lot.model.entity.UserVisit;
import com.task.lot.repository.UserVisitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserVisitService {
    private final UserVisitRepository userVisitRepository;
    private final ModelMapper modelMapper;

    public void register(UserVisitDTO userVisitDTO) {
        userVisitRepository.save(modelMapper.map(userVisitDTO, UserVisit.class));
    }

    public Page<UserVisitStatsResponse> getStatsForIp(String ip, Pageable pageable) {
        List<UserVisitStatsResponse> responseList = getStatsList(ip);
        return new PageImpl<>(responseList, pageable, responseList.size());
    }

    public Page<FullStatsResponse> getFullStats(Pageable pageable) {
        if (userVisitRepository.selectDistinctIps().isPresent()) {
            List<FullStatsResponse> statsList = userVisitRepository.selectDistinctIps().get().stream()
                    .map(s -> new FullStatsResponse(s, getStatsList(s))).toList();
            return new PageImpl<>(statsList, pageable, statsList.size());
        }
        throw new EntityNotFoundException();
    }

    private List<UserVisitStatsResponse> getStatsList(String ip) {
        if (userVisitRepository.selectUserVisit(ip).isPresent()) {
            return userVisitRepository.selectUserVisit(ip).get().stream()
                    .map(s -> new UserVisitStatsResponse(s.getDate(), s.getCount())).toList();
        }
        throw new EntityNotFoundException();
    }
}
