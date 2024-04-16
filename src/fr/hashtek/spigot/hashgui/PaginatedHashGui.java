package fr.hashtek.spigot.hashgui;

import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.page.HashPage;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PaginatedHashGui extends HashGui
{

    private List<HashPage> pages;
    private List<Integer> freeSlotIndexes;
    private int currentPageIndex;


    /**
     * Creates a new Paginated GUI.
     *
     * @param	title	GUI's title
     * @param	size	GUI's amount of lines (must be between 1 and 6).
     */
    public PaginatedHashGui(String title, int size)
    {
        super(title, size);
        this.setup();
    }

    /**
     * Creates a new Paginated GUI from an existing Inventory.
     *
     * @param	inventory	Inventory
     */
    public PaginatedHashGui(Inventory inventory)
    {
        super(inventory);
        this.setup();
    }


    /**
     * Setups the Paginated GUI.
     */
    private void setup()
    {
        this.pages = new ArrayList<HashPage>();
        this.createNewPage();
        this.setCurrentPageIndex(0);
    }

    /**
     * Finds and puts in a list every free slot indexes.
     */
    private void findFreeSlotIndexes()
    {
        this.freeSlotIndexes = new ArrayList<Integer>();

        for (int k = 0; k < super.getTotalSize(); k++) {
            final ItemStack item = super.getInventory().getItem(k);
            if (item == null || item.getType() == Material.AIR)
                this.freeSlotIndexes.add(k);
        }
    }

    /**
     * Sets the item as the previous page button.
     * FIXME: In the click action, if gui is not an instance of
     *        PaginatedHashGui, we may want to throw an error.
     *
     * @param   item        Item
     * @param   guiManager  Gui manager
     * @return  Returns itself.
     */
    public PaginatedHashGui setPreviousPageItem(HashItem item, HashGuiManager guiManager)
    {
        item.addClickHandler(
            new ClickHandler()
                .addAllClickTypes()
                .setClickAction((Player player, HashGui gui, ItemStack i, int slot) -> {
                    if (!(gui instanceof PaginatedHashGui))
                        return;

                    final PaginatedHashGui paginatedGui = (PaginatedHashGui) gui;
                    final int pageIndex = paginatedGui.getCurrentPageIndex() - 1;

                    if (pageIndex < 0)
                        return;

                    paginatedGui.setCurrentPageIndex(pageIndex);
                    paginatedGui.update(player);
                })
        );

        item.build(guiManager);
        return this;
    }

    /**
     * Sets the item as the next page button.
     * FIXME: In the click action, if gui is not an instance of
     *        PaginatedHashGui, we may want to throw an error.
     *
     * @param   item        Item
     * @param   guiManager  Gui manager
     * @return  Returns itself.
     */
    public PaginatedHashGui setNextPageItem(HashItem item, HashGuiManager guiManager)
    {
        item.addClickHandler(
            new ClickHandler()
                .addAllClickTypes()
                .setClickAction((Player player, HashGui gui, ItemStack i, int slot) -> {
                    if (!(gui instanceof PaginatedHashGui))
                        return;

                    final PaginatedHashGui paginatedGui = (PaginatedHashGui) gui;
                    final int pageIndex = paginatedGui.getCurrentPageIndex() + 1;

                    if (pageIndex >= this.pages.size())
                        return;

                    paginatedGui.setCurrentPageIndex(pageIndex);
                    paginatedGui.update(player);
                })
        );

        item.build(guiManager);
        return this;
    }

    /**
     * Updates the GUI according to the current page.
     *
     * @param   player  Player
     * @return  Returns itself.
     */
    @Override
    public PaginatedHashGui update(Player player)
    {
        final HashPage currentPage = this.getCurrentPage();

        this.clearPageContent();
        for (int slot : currentPage.getItems().keySet()) {
            final HashItem item = currentPage.getItem(slot);
            super.setItem(slot, item);
        }

        super.update(player);
        return this;
    }

    /**
     * Visually clears every free slot.
     *
     * @return  Returns itself.
     */
    public PaginatedHashGui clearPageContent()
    {
        if (this.freeSlotIndexes == null)
            this.findFreeSlotIndexes();

        for (int k : this.freeSlotIndexes)
            super.getInventory().setItem(k, null);
        return this;
    }

    /* Page management */

    /**
     * Creates a fresh new page.
     *
     * @return  Created page
     */
    public HashPage createNewPage()
    {
        final HashPage page = new HashPage(this);

        this.pages.add(page);
        return page;
    }

    /**
     * Clears all pages.
     *
     * @return  Returns itself.
     */
    public PaginatedHashGui clearPages()
    {
        this.pages.clear();
        this.createNewPage();
        this.setCurrentPageIndex(0);
        return this;
    }

    /**
     * @param   index  Page index
     * @return  Page at index
     */
    public HashPage getPage(int index)
    {
        return this.pages.get(index);
    }

    /**
     * @return  All pages
     */
    public List<HashPage> getPages()
    {
        return this.pages;
    }

    /**
     * @return  Last page
     */
    public HashPage getLastPage()
    {
        return this.pages.get(this.pages.isEmpty() ? 0 : this.pages.size() - 1);
    }

    /**
     * @return  Current page
     */
    public HashPage getCurrentPage()
    {
        return this.getPage(this.getCurrentPageIndex());
    }

    /**
     * @return  Current page index
     */
    public int getCurrentPageIndex()
    {
        return this.currentPageIndex;
    }

    /**
     * @param   index   Page index
     */
    public void setCurrentPageIndex(int index)
    {
        this.currentPageIndex = index;
    }

}
