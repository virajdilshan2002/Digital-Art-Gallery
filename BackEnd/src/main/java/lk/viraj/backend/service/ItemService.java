package lk.viraj.backend.service;

import lk.viraj.backend.dto.ItemDTO;
import lk.viraj.backend.dto.other.ItemFormDataDTO;

public interface ItemService {
    int saveItem(String userName, ItemFormDataDTO itemFormDataDTO);

    ItemDTO convertToItemDTO(ItemFormDataDTO itemFormDataDTO);
}
