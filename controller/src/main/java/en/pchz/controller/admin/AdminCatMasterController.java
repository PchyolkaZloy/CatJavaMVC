package en.pchz.controller.admin;

import en.pchz.common.request.CatMasterRequest;
import en.pchz.common.response.CatMasterResponse;
import en.pchz.common.response.CatResponse;
import en.pchz.dto.CatColorDto;
import en.pchz.dto.CatDto;
import en.pchz.dto.CatMasterDto;
import en.pchz.exception.CatMasterServiceException;
import en.pchz.exception.CatServiceException;
import en.pchz.service.CatMasterService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/catMasterMenu")
@RequiredArgsConstructor
public class AdminCatMasterController {
    private final CatMasterService catMasterService;

    @GetMapping("/{id}")
    public CatMasterResponse findCatMasterById(@PathVariable("id") Integer catMasterId, HttpServletResponse response) {
        try {
            response.setStatus(HttpStatus.OK.value());
            var catMasterDto = catMasterService.findCatMasterById(catMasterId);

            return new CatMasterResponse(
                    catMasterDto.getId(),
                    catMasterDto.getName(),
                    catMasterDto.getBirthDate(),
                    catMasterDto.getCats().stream()
                            .map(catDto -> new CatResponse(
                                    catDto.getId(),
                                    catDto.getName(),
                                    catDto.getBirthDate(),
                                    catDto.getBreed(),
                                    catDto.getColor().toString(),
                                    catDto.getCatMaster().getId()
                            )).toList());
        } catch (CatMasterServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());

            return null;
        }
    }

    @PostMapping("/create")
    public void createCatMaster(@RequestBody CatMasterRequest request, HttpServletResponse response) {
        if (request.name() != null && request.birthdate() != null) {
            catMasterService.createCatMaster(new CatMasterDto(request.name(), request.birthdate()));
            response.setStatus(HttpStatus.CREATED.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }


    @PatchMapping("/addCatToCatMaster")
    public void addCatToCatMaster(
            @RequestParam("catMasterId") Integer catMasterId,
            @RequestParam("catId") Integer catId,
            HttpServletResponse response) {
        try {
            catMasterService.addCatToCatMaster(catMasterId, catId);
            response.setStatus(HttpStatus.OK.value());
        } catch (CatMasterServiceException | CatServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/getCatMasters")
    public List<CatMasterResponse> findAllCatMasters(HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        var catMasterDtoList = catMasterService.findAllCatMasters();

        return catMasterDtoList.stream()
                .map(catMasterDto -> new CatMasterResponse(
                        catMasterDto.getId(),
                        catMasterDto.getName(),
                        catMasterDto.getBirthDate(),
                        catMasterDto.getCats().stream()
                                .map(catDto -> new CatResponse(
                                        catDto.getId(),
                                        catDto.getName(),
                                        catDto.getBirthDate(),
                                        catDto.getBreed(),
                                        catDto.getColor().toString(),
                                        catDto.getCatMaster().getId()
                                )).toList()
                )).toList();
    }

    @GetMapping("/getCats")
    public List<CatResponse> findAllCats(@RequestParam("catMasterId") Integer catMasterId, HttpServletResponse response) {
        try {
            response.setStatus(HttpStatus.OK.value());
            List<CatDto> catDtoList = catMasterService.findAllCats(catMasterId);

            return catDtoList.stream()
                    .map(catDto -> new CatResponse(
                            catDto.getId(),
                            catDto.getName(),
                            catDto.getBirthDate(),
                            catDto.getBreed(),
                            catDto.getColor().toString(),
                            catDto.getCatMaster().getId()
                    )).toList();
        } catch (CatMasterServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());

            return null;
        }
    }

    @GetMapping("/getCatsByColor")
    public List<CatResponse> findAllCatsByColor(
            @RequestParam("catMasterId") Integer catMasterId,
            @RequestParam("color") String colorString,
            HttpServletResponse response) {
        try {
            response.setStatus(HttpStatus.OK.value());
            List<CatDto> catDtoList = catMasterService.findAllCatsByColor(catMasterId, CatColorDto.fromString(colorString));

            return catDtoList.stream()
                    .map(catDto -> new CatResponse(
                            catDto.getId(),
                            catDto.getName(),
                            catDto.getBirthDate(),
                            catDto.getBreed(),
                            catDto.getColor().toString(),
                            catDto.getCatMaster().getId()
                    )).toList();
        } catch (CatMasterServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());

            return null;
        }
    }

    @GetMapping("/getCatsByBreed")
    public List<CatResponse> findAllCatsByBreed(
            @RequestParam("catMasterId") Integer catMasterId,
            @RequestParam("breed") String breed,
            HttpServletResponse response) {
        try {
            response.setStatus(HttpStatus.OK.value());
            List<CatDto> catDtoList = catMasterService.findAllCatsByBreed(catMasterId, breed);

            return catDtoList.stream()
                    .map(catDto -> new CatResponse(
                            catDto.getId(),
                            catDto.getName(),
                            catDto.getBirthDate(),
                            catDto.getBreed(),
                            catDto.getColor().toString(),
                            catDto.getCatMaster().getId()
                    )).toList();
        } catch (CatMasterServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());

            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCatMasterById(@PathVariable("id") Integer catMasterId, HttpServletResponse response) {
        try {
            catMasterService.deleteCatMasterById(catMasterId);
            response.setStatus(HttpStatus.OK.value());
        } catch (CatMasterServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}
