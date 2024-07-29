package en.pchz.controller.admin;


import en.pchz.common.request.CatRequest;
import en.pchz.common.response.CatResponse;
import en.pchz.dto.CatColorDto;
import en.pchz.dto.CatDto;
import en.pchz.exception.CatMasterServiceException;
import en.pchz.exception.CatServiceException;
import en.pchz.service.CatService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/catMenu")
@RequiredArgsConstructor
public class AdminCatController {
    private final CatService catService;

    @GetMapping("/{id}")
    public CatResponse findCatById(@PathVariable("id") Integer catId, HttpServletResponse response) {
        try {
            var catDto = catService.findCatById(catId);

            response.setStatus(HttpStatus.OK.value());
            return new CatResponse(
                    catDto.getId(),
                    catDto.getName(),
                    catDto.getBirthDate(),
                    catDto.getBreed(),
                    catDto.getColor().toString(),
                    catDto.getCatMaster().getId());
        } catch (CatServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());

            return null;
        }
    }

    @PostMapping("/create")
    public void createCat(@RequestBody CatRequest request, HttpServletResponse response) {
        if (request.name() != null && request.birthdate() != null
                && request.breed() != null && request.color() != null) {
            catService.createCat(
                    new CatDto(
                            request.name(),
                            request.birthdate(),
                            request.breed(),
                            CatColorDto.fromString(request.color())
                    ));

            response.setStatus(HttpStatus.CREATED.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @PatchMapping("/registerFriendship")
    public void addFriendship(
            @RequestParam("firstId") Integer firstCatId,
            @RequestParam("secondId") Integer secondCatId,
            HttpServletResponse response) {
        try {
            response.setStatus(HttpStatus.OK.value());
            catService.createFriendship(firstCatId, secondCatId);
        } catch (CatServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/getCats")
    public List<CatResponse> findAllCats(HttpServletResponse response) {
        try {
            List<CatDto> catDtoList = catService.findAll();
            response.setStatus(HttpStatus.OK.value());

            return catDtoList.stream()
                    .map(catDto -> new CatResponse(
                            catDto.getId(),
                            catDto.getName(),
                            catDto.getBirthDate(),
                            catDto.getBreed(),
                            catDto.getColor().toString(),
                            catDto.getCatMaster().getId()
                    )).toList();

        } catch (UsernameNotFoundException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            return null;
        } catch (CatMasterServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());

            return null;
        }
    }

    @GetMapping("/getAllCatFriends")
    public List<CatResponse> findAllFriends(@RequestParam("id") Integer catId, HttpServletResponse response) {
        try {
            var catDtoList = catService.findAllFriends(catId);
            response.setStatus(HttpStatus.OK.value());

            return catDtoList.stream()
                    .map(catDto -> new CatResponse(
                            catDto.getId(),
                            catDto.getName(),
                            catDto.getBirthDate(),
                            catDto.getBreed(),
                            catDto.getColor().toString(),
                            catDto.getCatMaster().getId())).toList();
        } catch (CatServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());

            return null;
        }
    }

    @GetMapping("/getCatsByColor")
    public List<CatResponse> findAllCatsByColor(
            @RequestParam("color") String colorString,
            HttpServletResponse response) {
        try {
            List<CatDto> catDtoList = catService.findAllCatsByColor(CatColorDto.fromString(colorString));
            response.setStatus(HttpStatus.OK.value());

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
            @RequestParam("breed") String breed,
            HttpServletResponse response) {
        try {
            List<CatDto> catDtoList = catService.findAllCatsByBreed(breed);
            response.setStatus(HttpStatus.OK.value());

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
    public void deleteCatById(@PathVariable("id") Integer catId, HttpServletResponse response) {
        try {
            catService.deleteCatById(catId);
            response.setStatus(HttpStatus.OK.value());
        } catch (CatServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}
