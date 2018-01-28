import {Entity} from 'platform/services/entity';
import {EntityFormScope} from 'platform/alm-entity-form/services/entity-form-scope';

export interface SharedSpaceEntity extends Entity {
	schema_name: string;
	schema_password: string;
}

interface IIssueFooterScope extends EntityFormScope {
	status: string;
}

class IssueExtendedFieldsFooterController implements ng.IController {

	private loadStatus: boolean = false;
	private loadMessage : string;
	private retrievalStatus: boolean = true;
	private explanation: string;
	private tips: string;
	private summary: string;
	private recommendations: string;

	/*@ngInject*/
	constructor(private $scope: IIssueFooterScope,
				private securityToolService, private translate, private $sce) {
		var issueId = $scope.entity.id;
		this.loadMessage = translate('qc:retrieve-additional-data');
		this.loadStatus = true;
		this.retrievalStatus = true;
		this.securityToolService.getIssueAdditionalField(issueId).then((details)=> {
			this.explanation =  $sce.getTrustedHtml($sce.trustAsHtml(details.data.explanation));
			this.tips = $sce.getTrustedHtml($sce.trustAsHtml(details.data.tips));
			this.summary = $sce.getTrustedHtml($sce.trustAsHtml(details.data.summary));
			this.recommendations = $sce.getTrustedHtml($sce.trustAsHtml(details.data.recommendations));
			this.loadStatus = false;
		}, (reason)=> {
			this.retrievalStatus = false;
			this.loadStatus = false;
		});
	}
}

angular.module('alm-translations').config(function (translateProvider) {
	translateProvider.declareMessages({
		'qc:retrieve-additional-data': 'Retrieving additional data from Fortify On Demand'

	});
});

angular.module('pipeline-management').controller('issue-extended-fields-footer-controller', IssueExtendedFieldsFooterController);
