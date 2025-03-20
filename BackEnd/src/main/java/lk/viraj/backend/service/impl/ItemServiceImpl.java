package lk.viraj.backend.service.impl;

import lk.viraj.backend.dto.ItemDTO;
import lk.viraj.backend.dto.other.ItemFormDataDTO;
import lk.viraj.backend.entity.User;
import lk.viraj.backend.repo.ItemRepository;
import lk.viraj.backend.repo.UserRepository;
import lk.viraj.backend.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveItem(String userName, ItemFormDataDTO itemFormDataDTO) {
        //get user
        User user = userRepository.findByEmail(userName);

        //save image file in resources
        MultipartFile image = itemFormDataDTO.getImage();

        //convert form data to entity type
        return 0;
    }

    @Override
    public ItemDTO convertToItemDTO(ItemFormDataDTO itemFormDataDTO) {
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setName(itemFormDataDTO.getName());
        itemDTO.setDescription(itemFormDataDTO.getDescription());
        itemDTO.setImage(itemFormDataDTO.getImage().getOriginalFilename());
        itemDTO.setPrice(BigDecimal.valueOf(itemFormDataDTO.getPrice()));
        return null;
    }
}
