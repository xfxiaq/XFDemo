package com.xfhy.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 分页数据对象。
 * 
 * @author liuyg
 * @version 1.0
 * @param <T>
 */
public class PageDTO<T> implements Page<T>, Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 8051119530402452882L;
    /** 一页的数据 */
    private final List<T> content = new ArrayList<T>();
    /** 分页查询的基本信息 */
    private Pageable pageable;
    /** 总的数据件数 */
    private long total;
    
    private String _backcode = "0";
    
    /**
     * 空构造函数。
     */
    public PageDTO() {
        // UnitTest 用
    }
    
    /**
     * 构造函数。
     * 
     * @param pageable 分页信息
     * @param content 一页的数据
     * @param total 总数据件数
     */
    public PageDTO(final Pageable pageable, final List<T> content, final long total) {
        if (content != null) {
            this.content.addAll(content);
        }
        this.pageable = pageable;
        this.total = total;
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getNumber()
     */
    @Override
    public int getNumber() {
        return this.pageable == null ? 0 : this.pageable.getPageNumber();
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getSize()
     */
    @Override
    public int getSize() {
        return this.pageable == null ? 0 : this.pageable.getPageSize();
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getTotalPages()
     */
    @Override
    public int getTotalPages() {
        return this.getSize() == 0 ? 0 : (int) Math.ceil((double) this.total / (double) this.getSize());
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getNumberOfElements()
     */
    @Override
    public int getNumberOfElements() {
        return this.content.size();
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getTotalElements()
     */
    @Override
    public long getTotalElements() {
        return this.total;
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#hasPreviousPage()
     */
    @Override
    public boolean hasPreviousPage() {
        return this.getNumber() > 0;
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#isFirstPage()
     */
    @Override
    public boolean isFirstPage() {
        return !this.hasPreviousPage();
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#hasNextPage()
     */
    @Override
    public boolean hasNextPage() {
        return ((this.getNumber() + 1) * this.getSize()) < this.total;
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#isLastPage()
     */
    @Override
    public boolean isLastPage() {
        return !this.hasNextPage();
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#iterator()
     */
    @Override
    public Iterator<T> iterator() {
        return this.content.iterator();
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getContent()
     */
    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#hasContent()
     */
    @Override
    public boolean hasContent() {
        return !this.content.isEmpty();
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getSort()
     */
    @Override
    public Sort getSort() {
        return this.pageable == null ? null : this.pageable.getSort();
    }
    
    
    
    public String get_backcode() {
		return _backcode;
	}

	public void set_backcode(String _backcode) {
		this._backcode = _backcode;
	}

	/*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        
        String contentType = "UNKNOWN";
        
        if (this.content.size() > 0) {
            contentType = this.content.get(0).getClass().getName();
        }
        
        return String.format("Page %s of %d containing %s instances", this.getNumber(), this.getTotalPages(),
                contentType);
    }
}
