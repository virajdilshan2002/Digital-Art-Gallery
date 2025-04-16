package lk.viraj.backend.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
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
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

        /*// Reattach the User and Category entities to the current session
        user = entityManager.merge(user);
        category = entityManager.merge(category);*/

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
        return new ItemDTO(itemFormDataDTO.getName(), path, itemFormDataDTO.getDescription(), itemFormDataDTO.getPrice(), itemFormDataDTO.getQty(), categoryDTO, userDTO);
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> itemList = itemRepository.findAll();
        Collections.reverse(itemList);
        return modelMapper.map(itemList, new TypeToken<List<ItemDTO>>() {}.getType());
    }

    @Override
    public List<ItemDTO> getOwnedItemsByUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        String jpql = "SELECT i FROM Item i WHERE i.user = :user";
        List<Item> items = entityManager.createQuery(jpql, Item.class)
                .setParameter("user", user)
                .getResultList();

        return modelMapper.map(items, new TypeToken<List<ItemDTO>>() {}.getType());
    }

    @Override
    public boolean deleteItemById(String itemId) {
        itemRepository.deleteById(UUID.fromString(itemId));
        return itemRepository.existsById(UUID.fromString(itemId));
    }

    @Override
    public ItemDTO getItemById(UUID iid) {
        Item item = itemRepository.findById(iid)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        return modelMapper.map(item, ItemDTO.class);
    }

    @Override
    public ItemDTO setUpdatedDetails(ItemDTO itemDTO, ItemFormDataDTO itemFormDataDTO) {
        ItemDTO updateItemDTO = modelMapper.map(itemFormDataDTO, ItemDTO.class);
        updateItemDTO.setImage(itemDTO.getImage());
        updateItemDTO.setUser(itemDTO.getUser());
        updateItemDTO.setCategory(itemDTO.getCategory());
        return updateItemDTO;
    }

}
