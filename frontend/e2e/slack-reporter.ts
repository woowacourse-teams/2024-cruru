/* eslint-disable class-methods-use-this */
import type { Reporter, FullConfig, Suite, TestCase, TestResult, FullResult } from '@playwright/test/reporter';
import path from 'path';

const getSlackMessage = ({
  all,
  passed,
  failed,
  skipped,
  duration,
  result,
}: {
  all: string;
  passed: string;
  failed: string;
  skipped: string;
  duration: string;
  result: string;
}) => ({
  blocks: [
    {
      type: 'header',
      text: {
        type: 'plain_text',
        text: '🏃 E2E 테스트가 실행되었습니다: ',
        emoji: true,
      },
    },
    {
      type: 'rich_text',
      elements: [
        {
          type: 'rich_text_section',
          elements: [],
        },
        {
          type: 'rich_text_list',
          style: 'bullet',
          elements: [
            {
              type: 'rich_text_section',
              elements: [
                {
                  type: 'text',
                  text: '실행 시각: ',
                },
                {
                  type: 'text',
                  text: `${new Date().toLocaleDateString('ko-KR', {
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric',
                    hour: 'numeric',
                    minute: 'numeric',
                    second: 'numeric',
                    hour12: true,
                  })}`,
                },
              ],
            },
            {
              type: 'rich_text_section',
              elements: [
                {
                  type: 'text',
                  text: '테스트 케이스 수: ',
                },
                {
                  type: 'text',
                  text: all,
                },
              ],
            },
          ],
        },
      ],
    },
    {
      type: 'divider',
    },
    {
      type: 'rich_text',
      elements: [
        {
          type: 'rich_text_section',
          elements: [
            {
              type: 'text',
              text: 'SUMMARY',
              style: {
                bold: true,
              },
            },
          ],
        },
      ],
    },
    {
      type: 'rich_text',
      elements: [
        {
          type: 'rich_text_list',
          style: 'bullet',
          indent: 0,
          border: 0,
          elements: [
            {
              type: 'rich_text_section',
              elements: [
                {
                  type: 'emoji',
                  name: 'hourglass',
                  unicode: '231b',
                  style: {
                    bold: true,
                  },
                },
                {
                  type: 'text',
                  text: ' 테스트 실행 시간: ',
                  style: {
                    bold: true,
                  },
                },
                {
                  type: 'text',
                  text: duration,
                },
              ],
            },
            {
              type: 'rich_text_section',
              elements: [
                {
                  type: 'emoji',
                  name: 'package',
                  unicode: '1f4e6',
                  style: {
                    bold: true,
                  },
                },
                {
                  type: 'text',
                  text: ' 테스트 결과: ',
                  style: {
                    bold: true,
                  },
                },
              ],
            },
          ],
        },
        {
          type: 'rich_text_list',
          style: 'bullet',
          indent: 1,
          border: 0,
          elements: [
            {
              type: 'rich_text_section',
              elements: [
                {
                  type: 'emoji',
                  name: 'white_check_mark',
                  unicode: '2705',
                },
                {
                  type: 'text',
                  text: ' 성공: ',
                },
                {
                  type: 'text',
                  text: passed,
                },
              ],
            },
            {
              type: 'rich_text_section',
              elements: [
                {
                  type: 'emoji',
                  name: 'x',
                  unicode: '274c',
                },
                {
                  type: 'text',
                  text: ' 실패: ',
                },
                {
                  type: 'text',
                  text: failed,
                },
              ],
            },
            {
              type: 'rich_text_section',
              elements: [
                {
                  type: 'emoji',
                  name: 'fast_forward',
                  unicode: '23e9',
                },
                {
                  type: 'text',
                  text: ' 건너뜀: ',
                },
                {
                  type: 'text',
                  text: skipped,
                },
              ],
            },
          ],
        },
      ],
    },
    {
      type: 'divider',
    },
    {
      type: 'rich_text',
      elements: [
        {
          type: 'rich_text_section',
          elements: [
            {
              type: 'text',
              text: result,
            },
          ],
        },
      ],
    },
  ],
});

class MyReporter implements Reporter {
  all = 0;

  passed = 0;

  failed = 0;

  skipped = 0;

  failsMessage = '';

  onBegin(_: FullConfig, suite: Suite) {
    this.all = suite.allTests().length;
  }

  onTestEnd(test: TestCase, result: TestResult) {
    const testDuration = `${(result.duration / 1000).toFixed(1)}s`;
    const fileName = path.basename(test.location.file);
    const testTitle = test.title;

    switch (result.status) {
      case 'failed':
      case 'timedOut':
        this.addFailMessage(
          `✘ ${fileName}:${test.location.line}:${test.location.column} › ${testTitle}(${testDuration})`,
        );
        this.failed += 1;
        break;
      case 'skipped':
        this.addFailMessage(
          `⚠️ ${fileName}:${test.location.line}:${test.location.column} › ${testTitle}(${testDuration})`,
        );
        this.skipped += 1;
        break;
      case 'passed':
        this.passed += 1;
        break;
      default:
        break;
    }
  }

  async onEnd(result: FullResult) {
    const blockKit = await this.getBlockKit(result);
    const webhookUrl = await process.env.SLACK_WEBHOOK_URL;

    if (!webhookUrl) {
      console.error('SLACK_WEBHOOK_URL 환경 변수가 설정되지 않았습니다.');
      return;
    }

    try {
      const response = await fetch(webhookUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(blockKit),
      });

      if (!response.ok) {
        console.error('Slack 메시지 전송 실패:', response.statusText);
      } else {
        console.log('Slack 메시지 전송 성공');
      }
    } catch (error) {
      console.error('Slack 메시지 전송 중 에러 발생:', error);
    }
  }

  private addFailMessage(message: string) {
    this.failsMessage += `\n${message}`;
  }

  private async getBlockKit(result: FullResult) {
    const { duration } = result;

    const resultBlockKit = getSlackMessage({
      all: `${this.all}`,
      passed: `${this.passed}개`,
      failed: `${this.failed}개`,
      skipped: `${this.skipped}개`,
      duration: `${(duration / 1000).toFixed(1)}s`,
      result: `${this.failsMessage ? `통과하지 못한 테스트\n${this.failsMessage}` : '👍 모든 테스트가 성공적으로 통과했습니다!'}`,
    });

    return resultBlockKit;
  }
}
export default MyReporter;
