package com.task.lot.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullStatsResponse {
    private String ip;
    private List<UserVisitStatsResponse> stats;
}
