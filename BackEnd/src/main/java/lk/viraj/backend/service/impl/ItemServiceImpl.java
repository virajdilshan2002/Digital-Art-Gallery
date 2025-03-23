package lk.viraj.backend.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lk.viraj.backend.dto.CategoryDTO;
import lk.viraj.backend.dto.ItemDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.dto.other.ItemFormDataDTO;
import lk.viraj.backend.entity.Category;
import lk.viraj.backend.entity.Item;
import lk.viraj.backend.entity.User;
import lk.viraj.backend.repo.CategoryRepository;
import lk.viraj.backend.repo.ItemRepository;
import lk.viraj.backend.repo.UserRepository;
import lk.viraj.backend.service.ItemService;
import lk.viraj.backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public int saveItem(ItemDTO itemDTO) {
        // Fetch the User and Category entities
        User user = userRepository.findByEmail(itemDTO.getUser().getEmail());
        Category category = categoryRepository.findByName(itemDTO.getCategory().getName());

        // Reattach the User and Category entities to the current session
        user = entityManager.merge(user);
        category = entityManager.merge(category);

        // Map ItemDTO to Item entity
        Item item = modelMapper.map(itemDTO, Item.class);

        // Attach the fetched User and Category to the Item
        item.setUser(user);
        item.setCategory(category);

        // Save the Item
        itemRepository.save(item);
        return VarList.Created;
    }

    @Override
    public ItemDTO convertToItemDTO(ItemFormDataDTO itemFormDataDTO, UserDTO userDTO, CategoryDTO categoryDTO, String path) {
        ItemDTO itemDTO = new ItemDTO(itemFormDataDTO.getName(), itemFormDataDTO.getDescription(), path, itemFormDataDTO.getPrice(), categoryDTO, userDTO);
        return itemDTO;
    }

}
