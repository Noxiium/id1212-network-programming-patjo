package service;

import java.util.ArrayList;
import model.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ResultRepository;

/**
 *
 * @author Johan S. Fredlund
 */

@Service
public class ResultService {
    
    private ResultRepository resultRepository;
    
    @Autowired
    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    /**
    * Retrieves all the results from the database.
    *
    * @return the list of all results from the database
    */
    public ArrayList<ResultDTO> getAllResultsFromDB(){
        ArrayList<ResultDTO> resultList = resultRepository.getAllResultsFromDB();
        return resultList;
    }
}
