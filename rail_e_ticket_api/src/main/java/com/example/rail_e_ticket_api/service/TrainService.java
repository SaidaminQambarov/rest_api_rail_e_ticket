package com.example.rail_e_ticket_api.service;

import com.example.rail_e_ticket_api.payload.TrainDto;
import com.example.rail_e_ticket_api.entity.Train;
import com.example.rail_e_ticket_api.exception.CustomException;
import com.example.rail_e_ticket_api.repository.TrainRepository;
import com.example.rail_e_ticket_api.response.ApiResponse;
import com.example.rail_e_ticket_api.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.rail_e_ticket_api.util.interfaces.ResponseConstants.NOT_FOUND;
import static com.example.rail_e_ticket_api.util.interfaces.ResponseConstants.SUCCESS;

@RequiredArgsConstructor
@Service
public class TrainService implements BaseService<TrainDto> {
    private final TrainRepository trainRepository;
    private final ModelMapper mapper;

    @Override
    public ApiResponse add(TrainDto trainDto) {
        checkTrain(trainDto.getCode());
        Train train = mapper.map(trainDto, Train.class);
        return new ApiResponse(SUCCESS, 200, train);
    }

    @Override
    public ApiResponse getById(UUID id) {
        Optional<Train> trainOptional = trainRepository.findById(id);
        if (trainOptional.isPresent()) {
            return new ApiResponse(SUCCESS, 200, trainOptional.get());
        }
        throw new CustomException(NOT_FOUND);
    }

    @Override
    public ApiResponse getList() {
        List<Train> trainList = trainRepository.findAll();
        return trainList.isEmpty() ? new ApiResponse(NOT_FOUND, 404)
                : new ApiResponse(SUCCESS, 200, trainList);
    }

    @Override
    public ApiResponse updateById(UUID id, TrainDto trainDto) {
        Optional<Train> trainRepositoryById = trainRepository.findById(id);
        if (trainRepositoryById.isPresent()) {
            Train train = mapper.map(trainDto, Train.class);
            train.setId(id);
            Train train1 = trainRepository.save(train);
            return new ApiResponse(SUCCESS, 200, train1);
        }
        throw new CustomException(NOT_FOUND);
    }

    @Override
    public ApiResponse deleteById(UUID id) {
        Optional<Train> trainRepositoryById = trainRepository.findById(id);
        if (trainRepositoryById.isPresent()) {
            trainRepository.delete(trainRepositoryById.get());
            return new ApiResponse(SUCCESS, 200, trainRepositoryById.get());
        }
        throw new CustomException(NOT_FOUND);
    }

    private void checkTrain(String code) {
        Optional<Train> trainOptional = trainRepository.findByCode(code);

        if (trainOptional.isPresent())
            throw new CustomException("Destination with this " + code + " code is already exist");
    }
}
