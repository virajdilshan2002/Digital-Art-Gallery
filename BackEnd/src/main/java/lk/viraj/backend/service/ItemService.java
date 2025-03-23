package lk.viraj.backend.service;

import lk.viraj.backend.dto.CategoryDTO;
import lk.viraj.backend.dto.ItemDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.dto.other.ItemFormDataDTO;

public interface ItemService {
    int saveItem(ItemDTO itemDTO);

    ItemDTO convertToItemDTO(ItemFormDataDTO itemFormDataDTO, UserDTO userDTO, CategoryDTO categoryDTO, String path);
}
