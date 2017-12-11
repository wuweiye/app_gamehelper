package cn.dkm.gamehelper.model;

import java.util.List;

import com.ab.model.AbResult;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleListResult extends AbResult {

	private List<Article> items;
}
