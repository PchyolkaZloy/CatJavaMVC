package en.pchz.controller.user;

import en.pchz.common.request.CatRequest;
import en.pchz.common.response.CatResponse;
import en.pchz.dto.CatColorDto;
import en.pchz.dto.CatDto;
import en.pchz.exception.CatMasterServiceException;
import en.pchz.exception.CatServiceException;
import en.pchz.service.CatMasterService;
import en.pchz.service.CatService;
import en.pchz.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/cat")
@RequiredArgsConstructor
public class UserCatController {
    private final UserService userService;
    private final CatService catService;
    private final CatMasterService catMasterService;

    @GetMapping("/{id}")
    public CatResponse findCatById(@PathVariable("id") Integer catId, HttpServletResponse response) {
        try {
            Integer currentCatMasterId = getCurrentCatMasterId(response);
            if (currentCatMasterId == null) {
                return null;
            }

            var catDto = catMasterService.findCatById(currentCatMasterId, catId);
            if (catDto == null) {
                throw new CatServiceException("Error! No cats by this ID!");
            }
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
        if (request.name() != null && request.birthdate() != null && request.breed() != null && request.color() != null) {
            Integer currentCatMasterId = getCurrentCatMasterId(response);
            if (currentCatMasterId == null) {
                return;
            }

            Integer newCatId = catService.createCatAndReturnId(
                    new CatDto(
                            request.name(),
                            request.birthdate(),
                            request.breed(),
                            CatColorDto.fromString(request.color())
                    ));
            catMasterService.addCatToCatMaster(currentCatMasterId, newCatId);
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
            Integer currentCatMasterId = getCurrentCatMasterId(response);
            if (currentCatMasterId == null) {
                return;
            }
            if (catMasterService.findCatById(currentCatMasterId, firstCatId) == null) {
                throw new CatServiceException("Error! No cats by this ID!");
            }

            response.setStatus(HttpStatus.OK.value());
            catService.createFriendship(firstCatId, secondCatId);
        } catch (CatServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/getAllCatFriends")
    public List<CatResponse> findAllFriends(@RequestParam("id") Integer catId, HttpServletResponse response) {
        try {
            Integer currentCatMasterId = getCurrentCatMasterId(response);
            if (currentCatMasterId == null) {
                return null;
            }
            if (catMasterService.findCatById(currentCatMasterId, catId) == null) {
                throw new CatServiceException("Error! No cats by this ID!");
            }

            response.setStatus(HttpStatus.OK.value());
            var catDtoList = catService.findAllFriends(catId);

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

    @GetMapping("/getCats")
    public List<CatResponse> findAllCats(HttpServletResponse response) {
        try {
            Integer currentCatMasterId = getCurrentCatMasterId(response);
            if (currentCatMasterId == null) {
                return null;
            }

            List<CatDto> catDtoList = catMasterService.findAllCats(currentCatMasterId);
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

    @GetMapping("/getCatsByColor")
    public List<CatResponse> findAllCatsByColor(
            @RequestParam("color") String colorString,
            HttpServletResponse response) {
        try {
            Integer currentCatMasterId = getCurrentCatMasterId(response);
            if (currentCatMasterId == null) {
                return null;
            }

            List<CatDto> catDtoList = catMasterService
                    .findAllCatsByColor(currentCatMasterId, CatColorDto.fromString(colorString));
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
            Integer currentCatMasterId = getCurrentCatMasterId(response);
            if (currentCatMasterId == null) {
                return null;
            }

            List<CatDto> catDtoList = catMasterService.findAllCatsByBreed(currentCatMasterId, breed);
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
            Integer currentCatMasterId = getCurrentCatMasterId(response);
            if (currentCatMasterId == null) {
                return;
            }

            if (catMasterService.findCatById(currentCatMasterId, catId) == null) {
                throw new CatServiceException("Error! No cats by this ID!");
            }

            catService.deleteCatById(catId);
            response.setStatus(HttpStatus.OK.value());
        } catch (CatServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    private Integer getCurrentCatMasterId(HttpServletResponse response) {
        try {
            return userService
                    .findCatMasterIdByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (UsernameNotFoundException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            return null;
        }
    }
}
