/*Multi-threaded Text Search Program
      This program is a practice for multithreading in HCI design,
      using two threads: UI thread and the backgroundworker.
      The text searching thread is behind the UI thread; users have the 
      choice to cancel the search and there is a progress bar to indicate
      the searching progress.
  Author: Hao WAN (hxw161730)
  UTDallas 17S CS 6326 Human Computer Interaction
  Mar. 8, 2017
*/


using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Text.RegularExpressions;

namespace Text_Search
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }


       //browse button
        private void button1_Click(object sender, EventArgs e)
        {
            OpenFileDialog openf = new OpenFileDialog();
            openf.Filter = "Text File | *.txt";
            openf.ShowDialog();
            textBox1.Text = openf.FileName;
        }

        //search and cancel button
        private void button2_Click(object sender, EventArgs e)
        {
            if (button2.Text == "Search")
            {
                button2.Text = "Cancel";
                button1.Enabled = false;
            }
            else if (button2.Text == "Cancel")
            {
                button2.Text = "Search";
                button1.Enabled = true;
            }

            if (!backgroundWorker1.IsBusy&& button2.Text == "Cancel")
            {
                listView1.Items.Clear();
                backgroundWorker1.RunWorkerAsync();
            }

            if (backgroundWorker1.IsBusy&& button2.Text == "Search")
            {
                backgroundWorker1.CancelAsync();                
            }
        }

        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {
            if (string.IsNullOrWhiteSpace(textBox1.Text))
                MessageBox.Show("Please choose a text file first. :)");
            else
            {              
                string searchtext = textBox2.Text;

                //read the source file
                StreamReader sr = new StreamReader(textBox1.Text);

                int lineCount = File.ReadLines(textBox1.Text).Count();
                //i: Line No.
                int i = 0;
                while(sr.Peek() > -1)
                {
                    i++;
                    //set pause here, 1000 = 1 second
                    Thread.Sleep(10);
                    string s = sr.ReadLine();
                    //case sensitive: if (s.Contains(searchtext))
                    //case in-sensitive 
                    bool contains = Regex.IsMatch(s, searchtext, RegexOptions.IgnoreCase);
                    if (contains)
                    {
                        ListViewItem item = new ListViewItem(i.ToString());
                        item.SubItems.Add(s);
                        if (listView1.InvokeRequired)
                        {
                            listView1.Invoke(new MethodInvoker(delegate
                            {
                                listView1.Items.Add(item);
                            }));
                        }
                        else
                        {
                            listView1.Items.Add(item);
                        }
                    }

                    backgroundWorker1.ReportProgress((i * 100) / lineCount);

                    if (backgroundWorker1.CancellationPending)
                    {
                        e.Cancel = true;
                        backgroundWorker1.ReportProgress(0);
                        return;
                    }
                }
                sr.Close();
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {
            if (textBox2.Text.Length > 0)
            {
                button2.Enabled = true;
            }
            else
            {
                button2.Enabled = false;
            }
        }

        private void backgroundWorker1_ProgressChanged(object sender, System.ComponentModel.ProgressChangedEventArgs e)
        {
            progressBar1.Value = e.ProgressPercentage;
            label3.Text = e.ProgressPercentage.ToString() + "% completed";
        }

        private void backgroundWorker1_RunWorkerCompleted(object sender, System.ComponentModel.RunWorkerCompletedEventArgs e)
        {
            button2.Text = "Search";
            if (e.Cancelled)
            {
                label3.Text = "Search cancelled.";
            }
            else if (e.Error != null)
            {
                label3.Text = "There was an error: " + e.Error.Message;
            }
            else
            {
                label3.Text = "Search completed.";
                button1.Enabled = true;
            }
        }

        private void button1_MouseMove(object sender, MouseEventArgs e)
        {
            button1.Cursor = Cursors.Hand;
        }

        private void button2_MouseMove(object sender, MouseEventArgs e)
        {
            button2.Cursor = Cursors.Hand;
        }
    }
}
