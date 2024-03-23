package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.DTO.EmployeeDTO;
import com.onedayoffer.taskdistribution.DTO.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskDistributorImpl implements TaskDistributor {

    @Override
    public void distribute(List<EmployeeDTO> employees, List<TaskDTO> tasks) {
        Map<Integer, List<TaskDTO>> tasksByPriority = new HashMap<>();

        for (TaskDTO task : tasks) {
            if (!tasksByPriority.containsKey(task.getPriority())) {
                tasksByPriority.put(task.getPriority(), new ArrayList<>());
            }
            tasksByPriority.get(task.getPriority()).add(task);
        }

        for (List<TaskDTO> taskList : tasksByPriority.values()) {
            taskList.sort(Comparator.comparing(TaskDTO::getLeadTime).reversed());
            for (TaskDTO task : taskList) {
                for (EmployeeDTO employee : employees) {
                    if (employee.getTotalLeadTime() + task.getLeadTime() > 420) {
                        break;
                    }
                    employee.getTasks().add(task);
                }
            }
        }
    }
}
